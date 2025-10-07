package com.well.sequencewordsapi.handlers

import com.well.sequencewordsapi.exceptions.GameFinishedException
import com.well.sequencewordsapi.exceptions.InvalidTurnPlayException
import com.well.sequencewordsapi.exceptions.InvalidTurnPlayWaitUntilAvailableException
import com.well.sequencewordsapi.exceptions.InvalidWordCountException
import com.well.sequencewordsapi.exceptions.RoomFullException
import com.well.sequencewordsapi.exceptions.RoomNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RoomExceptionHandler {

    @ExceptionHandler(RoomFullException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleException(ex: RoomFullException) = mapOf(
        "error" to "Room is full",
        "message" to ex.message
    )

    @ExceptionHandler(RoomNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(ex: RoomNotFoundException) = mapOf(
        "error" to "Room not found",
        "message" to ex.message
    )

    @ExceptionHandler(InvalidWordCountException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(ex: InvalidWordCountException) = mapOf(
        "error" to "Invalid word count",
        "message" to ex.message
    )

    @ExceptionHandler(InvalidTurnPlayException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(ex: InvalidTurnPlayException) = mapOf(
        "error" to "Invalid play",
        "message" to ex.message
    )

    @ExceptionHandler(InvalidTurnPlayWaitUntilAvailableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(ex: InvalidTurnPlayWaitUntilAvailableException) = mapOf(
        "error" to "Invalid play, wait until available",
        "message" to ex.message
    )

    @ExceptionHandler(GameFinishedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(ex: GameFinishedException) = mapOf(
        "error" to "Game finished",
        "message" to ex.message
    )

}