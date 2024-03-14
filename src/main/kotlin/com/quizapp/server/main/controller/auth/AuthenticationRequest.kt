package com.quizapp.server.main.controller.auth

import io.jsonwebtoken.security.Password

data class AuthenticationRequest(
        val username : String,
        val password: String,
)