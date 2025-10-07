package com.well.sequencewordsapi.http.controllers

import com.well.sequencewordsapi.http.requests.LoginDTO
import com.well.sequencewordsapi.http.responses.LoginResponse
import com.well.sequencewordsapi.services.TokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication", description = "User's authentication")
@RestController
@RequestMapping("/auth")
class AuthController(
    private var auth: AuthenticationManager,
    private val tokenService: TokenService
) {

    @Operation(summary = "Authenticate user", description = "Authenticate user with provided credentials")
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody data: LoginDTO
    ): ResponseEntity<LoginResponse> {
        println(data)

        val email = data.email.lowercase()
        val auth = auth.authenticate(UsernamePasswordAuthenticationToken(email, data.password))

        if (!auth.isAuthenticated) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(LoginResponse(
            token = tokenService.issue(email)
        ))
    }

}