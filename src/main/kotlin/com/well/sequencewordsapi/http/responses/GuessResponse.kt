package com.well.sequencewordsapi.http.responses

data class GuessResponse (
    val isCorrect: Boolean,
    val isFinished: Boolean,
)