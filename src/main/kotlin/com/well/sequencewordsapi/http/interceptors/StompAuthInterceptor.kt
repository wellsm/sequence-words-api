package com.well.sequencewordsapi.http.interceptors

import com.well.sequencewordsapi.exceptions.InvalidJwtException
import com.well.sequencewordsapi.services.TokenService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class StompAuthInterceptor(
    private val tokenService: TokenService
): ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val access = StompHeaderAccessor.wrap(message)

        if (access.command != StompCommand.CONNECT) {
            return message
        }

        val token = access.getFirstNativeHeader("authorization")?.replace("Bearer ", "")
            ?: throw IllegalArgumentException("Token is empty")

        try {
            val auth = tokenService.getUserFromToken(token)

            if (!auth.isAuthenticated) {
                throw IllegalArgumentException("Invalid token")
            }

            return message
        } catch (ex: Exception) {
            throw InvalidJwtException()
        }
    }

}