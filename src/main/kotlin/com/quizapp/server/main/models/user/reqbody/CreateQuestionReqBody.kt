package com.quizapp.server.main.models.user.reqbody

import com.quizapp.server.main.models.user.Question
import jakarta.persistence.*
import org.springframework.web.multipart.MultipartFile

data class CreateQuestionReqBody(
    val questionTextImg: MultipartFile,
    val questionOptionsImg : List<MultipartFile>,
    val topicId: Long,
    val levelId: Long,
    val category: String,
    val correctAnswer: String
)

fun CreateQuestionReqBody.toQuestion(questionTextImg : String , questionOptionsImg: List<String>) : Question {
    return Question(questionTextImg = questionTextImg , questionOptionsImg =  questionOptionsImg , category = category, correctAnswer = correctAnswer, levelId = levelId , topicId = topicId)
}