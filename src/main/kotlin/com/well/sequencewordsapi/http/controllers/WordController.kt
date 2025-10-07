package com.well.sequencewordsapi.http.controllers

import com.well.sequencewordsapi.http.requests.SelectWordsDTO
import com.well.sequencewordsapi.http.requests.TryWordDTO
import com.well.sequencewordsapi.http.responses.GuessResponse
import com.well.sequencewordsapi.http.responses.RoomResponse
import com.well.sequencewordsapi.models.User
import com.well.sequencewordsapi.services.PlayerService
import com.well.sequencewordsapi.services.RoomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Words", description = "Word's management")
@RestController
@RequestMapping("/rooms/{room}/words")
@SecurityRequirement(name = "jwt")
class WordController (
    private val roomService: RoomService,
    private val playerService: PlayerService
) {

    @Operation(summary = "Select Words")
    @PostMapping
    fun selectWords(
        @AuthenticationPrincipal user: User,
        @PathVariable room: String,
        @Valid @RequestBody data: SelectWordsDTO
    ): ResponseEntity<RoomResponse> {
        return roomService.selectWords(room, user, data)
            .let { ResponseEntity.ok(it) }
    }

    @Operation(summary = "Try Guess Word")
    @PostMapping("/guess")
    fun tryGuessWord(
        @AuthenticationPrincipal user: User,
        @PathVariable room: String,
        @Valid @RequestBody data: TryWordDTO
    ): ResponseEntity<GuessResponse> {
        return roomService.tryGuessWord(room, user, data)
            .let { ResponseEntity.ok(it) }
    }
}