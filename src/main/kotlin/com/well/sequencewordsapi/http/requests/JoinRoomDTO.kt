package com.well.sequencewordsapi.http.requests

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length

data class JoinRoomDTO(
    @field:Length(min = 2, max = 20, message = "Name must be between 2 and 20 characters long")
    @field:Schema(example = "Jane Doe", description = "Player's name")
    val name: String
)
