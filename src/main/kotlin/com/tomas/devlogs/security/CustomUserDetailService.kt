package com.tomas.devlogs.security
import com.tomas.devlogs.domain.repository.UserRepository
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found") }

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            listOf() // roles later
        )
    }
}