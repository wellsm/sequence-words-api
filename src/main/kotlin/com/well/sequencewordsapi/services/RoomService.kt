package com.well.sequencewordsapi.services

import com.well.sequencewordsapi.enums.RoomState
import com.well.sequencewordsapi.exceptions.GameFinishedException
import com.well.sequencewordsapi.exceptions.InvalidTurnPlayException
import com.well.sequencewordsapi.exceptions.InvalidWordCountException
import com.well.sequencewordsapi.exceptions.RoomFullException
import com.well.sequencewordsapi.exceptions.RoomNotFoundException
import com.well.sequencewordsapi.http.requests.CreatePlayerDTO
import com.well.sequencewordsapi.http.requests.CreateRoomDTO
import com.well.sequencewordsapi.http.requests.JoinRoomDTO
import com.well.sequencewordsapi.http.requests.SelectWordsDTO
import com.well.sequencewordsapi.http.requests.TryWordDTO
import com.well.sequencewordsapi.http.responses.GuessResponse
import com.well.sequencewordsapi.http.responses.RoomResponse
import com.well.sequencewordsapi.mappers.toEntity
import com.well.sequencewordsapi.mappers.toResponse
import com.well.sequencewordsapi.models.Player
import com.well.sequencewordsapi.models.Room
import com.well.sequencewordsapi.repositories.RoomRepository
import jakarta.transaction.Transactional
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RoomService (
    private val roomRepository: RoomRepository,
    private val playerService: PlayerService,
    private val wordService: WordService,
    private val messaging: SimpMessagingTemplate
) {



    @Transactional
    fun createRoom(data: CreateRoomDTO): RoomResponse {
        val room = roomRepository.save(data.toEntity())
        val me = playerService.createPlayer(CreatePlayerDTO(
            name = data.name,
            room = room
        ))

        return room.toResponse(me)
    }

    fun getRoomById(id: String): Room {
        return roomRepository.findById(id).orElseThrow()
    }

    @Transactional
    fun joinRoom(id: String, data: JoinRoomDTO): RoomResponse {
        val room = roomRepository.findById(id).orElseThrow {
            RoomNotFoundException(id)
        }

        if (room.players.size >= 2) {
            throw RoomFullException(id)
        }

        val me = playerService.createPlayer(CreatePlayerDTO(
            name = data.name,
            room = room,
            isOwner = false,
            seat = 1
        ))

        roomRepository.save(room.apply {
            state = RoomState.CHOOSING
        })

        val opponent = room.players.first { it.id != me.id }

        messaging.convertAndSend("/topic/room/${room.id}/player/${opponent.id}", room.toResponse(opponent))

        return room.toResponse(me)
    }

    @Transactional
    fun selectWords(id: String, me: Player, data: SelectWordsDTO): RoomResponse {
        val room = roomRepository.findById(id).orElseThrow() {
            RoomNotFoundException(id)
        }

        if (data.words.size != room.howManyWords) {
            throw InvalidWordCountException(room.howManyWords)
        }

        wordService.selectWords(room, me, data.words)
        playerService.playerIsReady(me)

        val allPlayersAreReady = room.players.all {
            it.isReady
        }

        if (allPlayersAreReady) {
            roomRepository.save(room.apply {
                state = RoomState.STARTED
                turn = (0..1).random()
            })

            val opponent = room.players.first { it.id != me.id }

            messaging.convertAndSend("/topic/room/${room.id}/player/${opponent.id}", room.toResponse(opponent, Instant.now()))
        }

        return room.toResponse(me)
    }

    @Transactional
    fun tryGuessWord(id: String, me: Player, data: TryWordDTO): GuessResponse {
        val room = roomRepository.findById(id).orElseThrow() {
            RoomNotFoundException(id)
        }

        if (room.state == RoomState.FINISHED) {
            throw GameFinishedException(id)
        }

        if (room.turn != me.seat) {
            throw InvalidTurnPlayException()
        }

        val opponent = room.players.first { it.id != me.id }
        val guess = wordService.tryGuessWord(opponent, data.word)

        roomRepository.save(room.apply {
            this.turn = if (guess.isCorrect) room.turn else opponent.seat
            this.state = if (guess.isFinished) RoomState.FINISHED else room.state
        })

        return guess
    }

    @Transactional
    fun passTurn(id: String, token: String): RoomResponse {
        val room = roomRepository.findById(id).orElseThrow {
            RoomNotFoundException(id)
        }

        val me = playerService.getPlayerByToken(token)

        if (room.turn != me.seat) {
            throw InvalidTurnPlayException()
        }

        val elapsed = (Instant.now().epochSecond - room.updatedAt.epochSecond)

        if (elapsed < room.duration * 0.75) {
            throw InvalidTurnPlayException()
        }

        val opponent = room.players.first { it.id != me.id }

        val allOpponentWordsAreRevealed = opponent.words.size == opponent.words.filter {
            it.revealed == it.value.length
        }.size

        roomRepository.save(room.apply {
            this.turn = room.players.first { it.id != me.id }.seat
            this.state = if (allOpponentWordsAreRevealed) RoomState.FINISHED else room.state
            this.winner = if (allOpponentWordsAreRevealed) me else null
            this.updatedAt = Instant.now()
        })

        if (room.winner == null) {
            wordService.revealNextLetter(opponent)
        }

        messaging.convertAndSend("/topic/room/${room.id}/player/${opponent.id}", room.toResponse(opponent))

        return room.toResponse(me)
    }
}