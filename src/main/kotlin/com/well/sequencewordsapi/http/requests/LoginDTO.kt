package com.well.sequencewordsapi.http.requests

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class LoginDTO(
    @field:NotBlank(message = "Email cannot be blank")
    @field:Schema(example = "john.doe@example.com", description = "Player's email")
    val email: String,
    @field:NotBlank(message = "Password cannot be blank")
    @field:Schema(example = "John@123", description = "Player's password")
    val password: String
)
