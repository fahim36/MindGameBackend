package com.quizapp.server.main.controller.registration

import com.quizapp.server.main.repository.user.UserType
import org.springframework.http.HttpStatus
import java.util.*

data class SignUpResponse(
        val status: HttpStatus,
        val message:String,
        val registeredUserInfo: RegisteredUserInfo? = null
)
data class RegisteredUserInfo(
        val id: Long?,
        val username : String,
        val role : UserType,
        val email:String,
        val hasLeaderBoardPermission: Boolean,
        val isColorBlind:Boolean,
)