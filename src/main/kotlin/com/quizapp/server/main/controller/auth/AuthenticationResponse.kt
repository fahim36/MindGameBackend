package com.quizapp.server.main.controller.auth

import com.quizapp.server.main.controller.registration.RegisteredUserInfo
import org.springframework.http.HttpStatus

data class AuthenticationResponse (val status: HttpStatus,val message : String, val accessToken : String, val userResponse: RegisteredUserInfo?)