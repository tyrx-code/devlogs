package com.tomas.devlogs.domain.service
import com.tomas.devlogs.domain.entity.User
import com.tomas.devlogs.domain.enums.Role
import com.tomas.devlogs.domain.repository.UserRepository
import com.tomas.devlogs.dto.AuthResponse
import com.tomas.devlogs.dto.LoginRequest
import com.tomas.devlogs.dto.RegisterRequest
import com.tomas.devlogs.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun login(request: LoginRequest): AuthResponse {

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )

        val user = userRepository.findByUsername(request.username)
            .orElseThrow()

        val token = jwtService.generateToken(user.username)

        return AuthResponse(
            token = token,
            username = user.username
        )
    }//End of login function.

    fun register(request: RegisterRequest): AuthResponse {

        if (userRepository.existsByUsername(request.username)) {
            throw RuntimeException("Username already taken")
        }

        if (userRepository.existsByEmail(request.email)) {
            throw RuntimeException("Email already taken")
        }

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER
        )

        userRepository.save(user)

        // For now return dummy token (we'll replace later)
        return AuthResponse(
            token = "dummy-token",
            username = user.username
        )
    }//End of register function.
}