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
    val words = if (isMe) {
        words.map { it.value.uppercase() }
    } else {
        words.mapIndexed { index, word ->
            if (index == 0) {
                word.value.uppercase()
            } else {
                word.value.take(word.revealed).uppercase()
            }
        }
    }

    return PlayerResponse(
        id = id,
        name = name,
        isOwner = isOwner,
        seat = seat,
        isReady = isReady,
        hash = hash,
        words = words
    )
}
