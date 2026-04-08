package com.tomas.devlogs.security
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    @Value("\${app.jwt.secret}") private val secretKey: String,
    @Value("\${app.jwt.expiration}") private val jwtExpiration: Long
) {

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray())
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    fun isTokenValid(token: String, username: String): Boolean {
        val extractedUsername = extractUsername(token)
        return (extractedUsername == username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractAllClaims(token).expiration.before(Date())
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey.toByteArray())
            .parseClaimsJws(token)
            .body
    }
}