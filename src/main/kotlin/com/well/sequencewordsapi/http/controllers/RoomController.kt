package com.well.sequencewordsapi.http.controllers

import com.well.sequencewordsapi.http.requests.CreateRoomDTO
import com.well.sequencewordsapi.http.requests.JoinRoomDTO
import com.well.sequencewordsapi.http.responses.RoomResponse
import com.well.sequencewordsapi.mappers.toResponse
import com.well.sequencewordsapi.models.User
import com.well.sequencewordsapi.services.PlayerService
import com.well.sequencewordsapi.services.RoomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Rooms", description = "Room's management")
@RestController
@RequestMapping("/rooms")
@SecurityRequirement(name = "jwt")
class RoomController (
    private val roomService: RoomService,
    private val playerService: PlayerService
) {
    @Operation(summary = "Create Room")
    @PostMapping
    fun createRoom(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody data: CreateRoomDTO
    ): ResponseEntity<RoomResponse> {
        return roomService.createRoom(user, data)
            .let { ResponseEntity.ok(it) }
    }

    @Operation(summary = "Get Room By Id")
    @GetMapping("/{room}")
    fun getRoomById(
        @AuthenticationPrincipal user: User,
        @PathVariable room: String,
    ): ResponseEntity<RoomResponse> {
        return roomService.getRoomById(user, room)
            .let { ResponseEntity.ok(it) }
    }

    @Operation(summary = "Join Room")
    @PostMapping("/{room}/join")
    fun joinRoom(
        @AuthenticationPrincipal user: User,
        @PathVariable room: String,
        @Valid @RequestBody data: JoinRoomDTO
    ): ResponseEntity<RoomResponse> {
        return roomService.joinRoom(user, room, data)
            .let { ResponseEntity.ok(it) }
    }

    @Operation(summary = "Pass Turn")
    @PostMapping("/{room}/pass")
    fun passTurn(
        @PathVariable room: String,
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<RoomResponse> {
        return roomService.passTurn(room, user)
            .let { ResponseEntity.ok(it) }
    }
}