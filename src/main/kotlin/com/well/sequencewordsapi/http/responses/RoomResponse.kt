package com.well.sequencewordsapi.http.responses

import io.swagger.v3.oas.annotations.media.Schema

data class RoomResponse (
    @field:Schema(example = "1", description = "Room's Code")
    val id: String,
    @field:Schema(example = "5", description = "How many words must be at least 5 and at most 10")
    val howManyWords: Int = 0,
    @field:Schema(example = "CREATED", description = "Is Game started")
    val state: String,
    @field:Schema(example = "0", description = "Current Turn")
    val turn: Int = 0,
    @field:Schema(description = "Me")
    val me: PlayerResponse,
    @field:Schema(description = "Opponent")
    val opponent: PlayerResponse? = null
)