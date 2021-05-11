package de.paderit.virtualoffice.server

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtTokenService(private val algorithm: Algorithm, private val expirationPeriod: Long, private val issuer: String) {

    fun generateToken(id: Int, username: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", id)
        .withClaim("login", username)
        .withExpiresAt(obtainExpirationDate())
        .sign(algorithm)

    private fun obtainExpirationDate() = Date(System.currentTimeMillis() + expirationPeriod)

    fun buildVerifier(algorithm: Algorithm, issuer: String): JWTVerifier =
        JWT.require(algorithm)
            .withIssuer(issuer)
            .build()
}