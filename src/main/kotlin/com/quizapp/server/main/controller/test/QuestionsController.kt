package com.quizapp.server.main.controller.test

import com.quizapp.server.main.Utils.uploadImageToFirebaseStorage
import com.quizapp.server.main.models.user.Question
import com.quizapp.server.main.models.user.reqbody.CreateQuestionReqBody
import com.quizapp.server.main.models.user.reqbody.toQuestion
import com.quizapp.server.main.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/api/users/questions")
class QuestionController {

    @Autowired
    private lateinit var questionService: QuestionService

    @PostMapping("/create")
    fun createQuestion(@ModelAttribute question: CreateQuestionReqBody): QuestionCreationResponse {
        val questionImage = uploadImageToFirebaseStorage(question.questionTextImg)
        val optionImages = uploadImageToFirebaseStorage(question.questionOptionsImg)
        val createdQuestion = questionService.createQuestion(question.toQuestion(questionImage,optionImages))
        return QuestionCreationResponse(HttpStatus.OK,"Created Successfully", question = createdQuestion)
    }

    @GetMapping("/getQuestionsByCategory")
    fun createQuestion(@RequestBody category: String): ResponseEntity<List<Question>> {
        val createdQuestion = questionService.getQuestionsByCategory(category)
        return ResponseEntity.ok(createdQuestion)
    }

    @DeleteMapping("/delete/{questionId}")
    fun deleteQuestion(@PathVariable questionId: Long): ResponseEntity<String> {
        questionService.deleteQuestionById(questionId)
        return ResponseEntity.ok("Question with ID $questionId deleted successfully")
    }

    @GetMapping("/getByTopicAndLevel/{topicId}/{levelId}")
    fun getQuestionsByTopicAndLevel(@PathVariable topicId: Long, @PathVariable levelId: Long): ResponseEntity<List<Question>> {
        val questions = questionService.getQuestionsByTopicAndLevel(topicId, levelId)
        return ResponseEntity.ok(questions)
    }
}