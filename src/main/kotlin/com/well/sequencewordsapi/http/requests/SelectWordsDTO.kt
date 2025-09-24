package com.well.sequencewordsapi.http.requests

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

data class SelectWordsDTO(
    @field:Schema(
        description = "Words to guess",
        type = "array",
        example = "[\"Hello\", \"World\", \"Earth\", \"Plant\", \"Tree\"]"
    )
    val words: List<String>
)
