package com.quizapp.server.main.controller.registration

import com.quizapp.server.main.repository.user.UserType
import org.springframework.http.HttpStatus
import java.util.UUID
data class SignUpResponse(
        val  httpStatus: HttpStatus,
        val message:String,
        val registeredUserInfo: RegisteredUserInfo
)
data class RegisteredUserInfo(
        val uuid: UUID,
        val username : String,
        val role : UserType,
        val email:String,
        val hasLeaderBoardPermission: Boolean,
        val isColorBlind:Boolean,
)