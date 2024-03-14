package com.quizapp.server.main.controller.auth

import com.quizapp.server.main.controller.registration.RegisteredUserInfo

data class AuthenticationResponse (val accessToken : String, val refreshToken : String, val userResponse: RegisteredUserInfo)