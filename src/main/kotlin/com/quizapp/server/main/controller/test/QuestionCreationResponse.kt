package com.quizapp.server.main.controller.test

import com.quizapp.server.main.controller.registration.RegisteredUserInfo
import com.quizapp.server.main.models.user.Question
import org.springframework.http.HttpStatus

data class QuestionCreationResponse (
val status: HttpStatus,
val message:String,
val question: Question? = null
)
