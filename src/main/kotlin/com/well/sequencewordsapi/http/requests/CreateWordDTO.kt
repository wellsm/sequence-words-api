package com.well.sequencewordsapi.http.requests

import com.well.sequencewordsapi.models.Player
import com.well.sequencewordsapi.models.Room

data class CreateWordDTO (
    val room: Room,
    val player: Player,
    val order: Int,
    val value: String
)