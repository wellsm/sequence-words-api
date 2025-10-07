package com.well.sequencewordsapi.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.well.sequencewordsapi.http.requests.CreatePlayerDTO
import com.well.sequencewordsapi.mappers.toEntity
import com.well.sequencewordsapi.models.Player
import com.well.sequencewordsapi.repositories.PlayerRepository
import org.springframework.stereotype.Service

@Service
class PlayerService (
    private val tokenService: TokenService,
    private val playerRepository: PlayerRepository
) {

    private val alg = Algorithm.HMAC256(System.getenv("PLAYER_TOKEN_SECRET") ?: "dev-secret-change")

    fun createPlayer(data: CreatePlayerDTO): Player {
        return playerRepository.save(data.toEntity())
    }

    fun playerIsReady(player: Player): Player {
        return playerRepository.save(player.apply {
            isReady = true
        })
    }

    fun getPlayerByToken(token: String): Player {
        val decoded = JWT.require(alg).build().verify(token)
        val id = decoded.subject.toLong()
        val player = playerRepository.findById(id).orElseThrow()

        return player
    }

}