package com.well.sequencewordsapi.http.responses

import io.swagger.v3.oas.annotations.media.Schema

data class PlayerResponse(
    @field:Schema(example = "1", description = "Player's id")
    val id: Long,
    @field:Schema(example = "John Doe", description = "Player's name")
    val name: String,
    @field:Schema(example = "true", description = "Is owner of the room")
    val isOwner: Boolean = false,
    @field:Schema(example = "0", description = "Player's seat")
    val seat: Int = 0,
    @field:Schema(example = "0", description = "Word's index to guess")
    val index: Int = 0,
    @field:Schema(example = "true", description = "Is ready to play")
    val isReady: Boolean = false,
    @field:Schema(example = "hash", description = "Player's hash")
    val hash: String? = null,
    @field:Schema(description = "Words to guess", type = "array")
    val words: List<WordResponse>
)
