package com.quizapp.server.main.service

import com.quizapp.server.main.config.JwtProperties
import com.quizapp.server.main.controller.auth.AuthenticationRequest
import com.quizapp.server.main.controller.auth.AuthenticationResponse
import com.quizapp.server.main.controller.registration.toResponse
import com.quizapp.server.main.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authRequest.username,
                        authRequest.password
                )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.username)
        val refreshToken = generateRefreshToken(user)
        val accessToken = generateAccessToken(user)
        refreshTokenRepository.save(refreshToken,user)
        val userDetails = userDetailsService.getUserDetailsByUserName(authRequest.username)
        return AuthenticationResponse(accessToken = accessToken, refreshToken = refreshToken,userDetails.toResponse())
    }


    fun refreshAccessToken(token: String): String? {
        val extractedUsername = tokenService.extractEmail(token)
        return extractedUsername?.let { username ->
            val currentUserDetails = userDetailsService.loadUserByUsername(username)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(token)

            if (!tokenService.isExpired(token) && currentUserDetails.username == refreshTokenUserDetails?.username)
                generateAccessToken(currentUserDetails)
            else
                null
        }
    }

    private fun generateRefreshToken(user: UserDetails): String {
        return tokenService.generate(userDetails = user, expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration))
    }

    private fun generateAccessToken(user: UserDetails): String {
        return tokenService.generate(
                user, Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )
    }


}
