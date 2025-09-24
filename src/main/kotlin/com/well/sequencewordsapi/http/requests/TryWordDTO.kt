package com.well.sequencewordsapi.http.requests

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

data class TryWordDTO(
    @field:Schema(example = "world", description = "Current word to guess",)
    val word: String
)
