package com.quizapp.server.main.controller.auth

data class AuthenticationRequest(
        val username : String,
        val password: String,
)