package com.well.sequencewordsapi.http.requests

import com.well.sequencewordsapi.models.Room
import com.well.sequencewordsapi.models.User

data class CreatePlayerDTO(
    val user: User,
    val name: String,
    val room: Room,
    val isOwner: Boolean = true,
    val seat: Int = 0
)
