package com.well.sequencewordsapi.security

import com.well.sequencewordsapi.models.User
import com.well.sequencewordsapi.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DatabaseUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): User {
        val email = username.lowercase().trim()

        return userRepository.findByEmail(email)
            .orElseThrow {
                UsernameNotFoundException("No user with email $email")
            }
    }
}
