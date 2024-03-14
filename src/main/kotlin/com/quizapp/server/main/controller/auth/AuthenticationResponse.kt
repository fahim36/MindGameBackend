package com.quizapp.server.main.controller.auth

import com.quizapp.server.main.controller.registration.RegResponse

data class AuthenticationResponse (val accessToken : String, val refreshToken : String, val userResponse: RegResponse)