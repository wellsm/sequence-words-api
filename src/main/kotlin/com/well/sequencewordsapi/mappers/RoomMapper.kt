package com.well.sequencewordsapi.mappers

import com.well.sequencewordsapi.http.responses.RoomResponse
import com.well.sequencewordsapi.models.Player
import com.well.sequencewordsapi.models.Room

fun Room.toResponse(me: Player): RoomResponse {
    return RoomResponse(
        id = id,
        howManyWords = howManyWords,
        state = state.toString(),
        turn = turn,
        me = me.toResponse(true),
        opponent = players.find { it.id != me.id }?.toResponse(false)
    )
}