package com.well.sequencewordsapi.http.requests

import com.well.sequencewordsapi.models.Room

data class CreatePlayerDTO(
    val name: String,
    val room: Room,
    val isOwner: Boolean = true,
    val seat: Int = 0
)
