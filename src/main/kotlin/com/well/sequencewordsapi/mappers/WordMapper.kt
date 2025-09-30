package com.well.sequencewordsapi.mappers

import com.well.sequencewordsapi.http.responses.WordResponse
import com.well.sequencewordsapi.models.Word

fun Word.toResponse(showLetters: Boolean): WordResponse {
    val value = if (showLetters) {
        this.value.uppercase()
    } else {
        if (this.index == 0) {
            this.value.uppercase()
        } else {
            this.value.take(this.revealed).uppercase()
        }
    }

    return WordResponse(
        id = this.id,
        index = this.index,
        value = value,
        revealed = this.revealed,
    )
}