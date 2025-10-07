package com.well.sequencewordsapi.mappers

import com.well.sequencewordsapi.http.requests.CreatePlayerDTO
import com.well.sequencewordsapi.http.responses.PlayerResponse
import com.well.sequencewordsapi.models.Player

fun CreatePlayerDTO.toEntity(): Player {
    return Player(
        user = user,
        name = name,
        room = room,
        isOwner = isOwner,
        seat = seat
    )
}

fun Player.toResponse(showLetters: Boolean, withWords: Boolean = true): PlayerResponse {


    return PlayerResponse(
        id = id,
        name = name,
        isOwner = isOwner,
        seat = seat,
        index = words.indexOfFirst { it.value.length != it.revealed },
        isReady = isReady,
        hash = hash,
        words = if (withWords) words.sortedBy { it.id }.map { it.toResponse(showLetters) } else emptyList()
    )
}
