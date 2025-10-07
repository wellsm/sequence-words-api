package com.well.sequencewordsapi.http.responses

data class LoginResponse(
    val token: String,
    val type: String = "Bearer"
)
