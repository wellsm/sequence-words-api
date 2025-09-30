package com.well.sequencewordsapi.mappers

import com.well.sequencewordsapi.enums.RoomState
import com.well.sequencewordsapi.http.requests.CreateRoomDTO
import com.well.sequencewordsapi.http.responses.RoomResponse
import com.well.sequencewordsapi.models.Player
import com.well.sequencewordsapi.models.Room
import java.time.Instant
import kotlin.collections.joinToString
import kotlin.collections.map
import kotlin.random.Random

private const val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

fun CreateRoomDTO.toEntity(): Room {
    val code = (1..6)
        .map { chars[Random.nextInt(chars.length)] }
        .joinToString("")

    return Room(
        id = code,
        howManyWords = howManyWords,
        duration = duration
    )
}

fun Room.toResponse(me: Player, updatedAt: Instant? = null): RoomResponse {
    return RoomResponse(
        id = id,
        howManyWords = howManyWords,
        state = state.toString(),
        turn = turn,
        duration = duration,
        me = me.toResponse(true),
        opponent = players.find { it.id != me.id }?.toResponse(state == RoomState.FINISHED),
        winner = winner?.toResponse(false),
        createdAt = createdAt.toString(),
        updatedAt = updatedAt?.toString() ?: this.updatedAt.toString()
    )
}