package com.quizapp.server.main.service

import com.quizapp.server.main.config.JwtProperties
import com.quizapp.server.main.controller.auth.AuthenticationRequest
import com.quizapp.server.main.controller.auth.AuthenticationResponse
import com.quizapp.server.main.controller.registration.SignUpResponse
import com.quizapp.server.main.controller.registration.toResponse
import com.quizapp.server.main.repository.RefreshTokenRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.*
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
    fun authentication(authRequest: AuthenticationRequest):  ResponseEntity<AuthenticationResponse> {
        try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.username,
                    authRequest.password
                )
            )
        }
        catch (e: Exception){
            return ResponseEntity.badRequest().body(AuthenticationResponse(status = HttpStatus.BAD_REQUEST,message = "Invalid UserName or Password", accessToken = "" , userResponse = null))

        }


        val user = userDetailsService.loadUserByUsername(authRequest.username)
        val accessToken = generateAccessToken(user)
//      val refreshToken = generateRefreshToken(user)
//      refreshTokenRepository.save(refreshToken,user)
        val userDetails = userDetailsService.getUserDetailsByUserName(authRequest.username)
        return ResponseEntity.ok().body(AuthenticationResponse(accessToken = accessToken, status = HttpStatus.OK, userResponse =  userDetails.toResponse(),message = "Successfully Logged In"))
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
