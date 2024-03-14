package com.quizapp.server.main.controller.registration

import com.quizapp.server.main.repository.user.UserType
import java.util.UUID

data class RegResponse(
        val uuid: UUID,
        val username : String,
        val role : UserType,
        val email:String,
        val hasLeaderBoardPermission: Boolean,
        val isColorBlind:Boolean,
)