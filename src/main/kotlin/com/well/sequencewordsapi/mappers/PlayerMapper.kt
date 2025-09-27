package com.well.sequencewordsapi.mappers

import com.well.sequencewordsapi.http.requests.CreatePlayerDTO
import com.well.sequencewordsapi.http.responses.PlayerResponse
import com.well.sequencewordsapi.models.Player

fun CreatePlayerDTO.toEntity(): Player {
    return Player(
        name = name,
        room = room,
        isOwner = isOwner,
        seat = seat
    )
}

fun Player.toResponse(isMe: Boolean): PlayerResponse {
    return PlayerResponse(
        id = id,
        name = name,
        isOwner = isOwner,
        seat = seat,
        index = if (words.isEmpty()) -1 else words.indexOfFirst { it.value.length != it.revealed },
        isReady = isReady,
        hash = hash,
        words = words.map { it.toResponse(isMe) }
    )
}
