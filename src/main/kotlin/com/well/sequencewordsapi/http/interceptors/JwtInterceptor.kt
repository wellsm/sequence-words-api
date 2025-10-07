package com.well.sequencewordsapi.http.interceptors

import com.well.sequencewordsapi.exceptions.InvalidJwtException
import com.well.sequencewordsapi.services.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtInterceptor(
    private val tokenService: TokenService,
    private val userDetailsService: UserDetailsService
): OncePerRequestFilter() {

    private val allowedPaths = listOf("/auth/login", "/ws", "/v3/api-docs", "/docs", "/favicon.ico")

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        val isPreflight = request.method.equals(HttpMethod.OPTIONS.toString(), ignoreCase = true)

        for (path in allowedPaths) {
            if (uri.startsWith(path)) {
                return true
            }
        }

        return isPreflight
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        val bearer = header.removePrefix("Bearer ").trim()

        try {
            val auth = tokenService.getUserFromToken(bearer)

            SecurityContextHolder.getContext().authentication = auth.apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }
        } catch (ex: Exception) {
            println("Invalid JWT token: ${ex.message}")

            throw InvalidJwtException()
        }

        filterChain.doFilter(request, response)
    }

}