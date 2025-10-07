package com.well.sequencewordsapi.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.well.sequencewordsapi.config.AuthProps
import com.well.sequencewordsapi.security.DatabaseUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class TokenService(
    private val props: AuthProps,
    private val userDetailsService: DatabaseUserDetailsService
) {

    private val alg = Algorithm.HMAC256(System.getenv("JWT_SECRET"))

    fun issue(email: String): String =
        JWT.create()
            .withIssuer(props.jwt.issuer)
            .withSubject(email)
            .withIssuedAt(Date())
            .withExpiresAt(Date.from(Instant.now().plusSeconds(props.jwt.ttlSeconds)))
            .sign(alg)

    fun verify(token: String) =
        JWT.require(alg)
            .withIssuer(props.jwt.issuer)
            .build()
            .verify(token)
            .subject

    fun getUserFromToken(token: String): UsernamePasswordAuthenticationToken {
        val subject = verify(token)
        val user = userDetailsService.loadUserByUsername(subject)
        val auth = UsernamePasswordAuthenticationToken(user, null, emptyList())

        return auth;
    }
}