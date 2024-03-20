package com.quizapp.server.main.controller.registration

import com.quizapp.server.main.repository.user.UserType

data class RegRequest(
        val username : String,
        val password: String,
        val role : UserType,
        val email:String = "",
        val hasLeaderBoardPermission: Boolean = false,
        val isColorBlind:Boolean = false,
)
