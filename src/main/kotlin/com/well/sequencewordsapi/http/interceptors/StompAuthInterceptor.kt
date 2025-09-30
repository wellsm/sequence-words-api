package com.well.sequencewordsapi.http.interceptors

import com.well.sequencewordsapi.services.PlayerService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import java.security.Principal

@Component
class StompAuthInterceptor(
    private val playerService: PlayerService
): ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val access = StompHeaderAccessor.wrap(message)

        if (access.command != StompCommand.CONNECT) {
            return message
        }

        val token = access.getFirstNativeHeader("authorization")?.replace("Bearer ", "")
            ?: throw IllegalArgumentException("Token is empty")

        val player = playerService.getPlayerByToken(token)

        access.user = Principal {
            player.name
        }

        return message
    }

}