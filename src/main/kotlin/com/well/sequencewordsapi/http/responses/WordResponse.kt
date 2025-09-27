package com.well.sequencewordsapi.http.responses

import io.swagger.v3.oas.annotations.media.Schema

data class WordResponse(
    @field:Schema(example = "1", description = "Word's id")
    val id: Long,
    @field:Schema(example = "0", description = "Word's index")
    val index: Int,
    @field:Schema(example = "HELLO", description = "Word")
    val value: String,
    @field:Schema(example = "5", description = "Number of letters revealed")
    val revealed: Int,
)
