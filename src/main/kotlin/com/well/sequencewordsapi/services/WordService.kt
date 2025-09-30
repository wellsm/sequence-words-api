package com.well.sequencewordsapi.services

import com.well.sequencewordsapi.http.responses.GuessResponse
import com.well.sequencewordsapi.models.Player
import com.well.sequencewordsapi.models.Room
import com.well.sequencewordsapi.models.Word
import com.well.sequencewordsapi.repositories.WordRepository
import org.springframework.stereotype.Service

@Service
class WordService (
    private val wordRepository: WordRepository
) {

    fun selectWords(
        room: Room,
        player: Player,
        words: List<String>
    ): List<Word> {
        if (room.words.filter { it.player == player }.size == words.size) {
            return room.words
        }

        val entities = words.mapIndexed { index, word ->
            Word(
                room = room,
                player = player,
                index = index,
                value = word.trim().uppercase(),
                revealed = if (index == 0) {
                    word.length
                } else {
                    1
                }
            )
        }

        val words = wordRepository.saveAll(entities)

        return words
    }

    fun tryGuessWord(player: Player, guess: String): GuessResponse {
        val word = player.words.sortedBy {
            it.index
        }.first {
            it.revealed < it.value.length
        }

        val guessIsCorrect = guess.equals(word.value, ignoreCase = true)

        wordRepository.save(word.apply {
            revealed = if (guessIsCorrect) {
                word.value.length
            } else {
                word.revealed + 1
            }
        })

        return GuessResponse(
            isCorrect = guessIsCorrect,
            isFinished = player.words.size == player.words.filter {
                it.revealed == it.value.length
            }.size
        )
    }

    fun revealNextLetter(player: Player) {
        val word = player.words.sortedBy {
            it.index
        }.first {
            it.revealed < it.value.length
        }

        wordRepository.save(word.apply {
            revealed = word.revealed + 1
        })
    }
}