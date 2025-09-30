package com.well.sequencewordsapi.http.requests

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.Length

data class CreateRoomDTO(
    @field:Schema(example = "John Doe", description = "Player's name")
    @field:Length(min = 2, max = 20, message = "Name must be between 2 and 20 characters long")
    val name: String,
    @field:Schema(example = "5", description = "How many words must be at least 5 and at most 10")
    @field:Min(value = 5, message = "How many words must be at least 5")
    @field:Max(value = 10, message = "How many words must be at most 10")
    var howManyWords: Int = 5,
    @field:Schema(example = "90", description = "Duration of the game in seconds")
    @field:Min(value = 30, message = "Duration of the game must be at least 30 seconds")
    @field:Max(value = 5 * 60, message = "Duration of the game must be at most 300 seconds (5 minutes)")
    var duration: Int = 90
)
