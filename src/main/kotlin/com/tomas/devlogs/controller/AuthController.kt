package com.tomas.devlogs.controller
import com.tomas.devlogs.domain.service.AuthService
import com.tomas.devlogs.dto.AuthResponse
import com.tomas.devlogs.dto.LoginRequest
import com.tomas.devlogs.dto.RegisterRequest
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): AuthResponse {
        return authService.register(request)
    }

    @Operation(security = [])
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): AuthResponse {
        return authService.login(request)
    }
}