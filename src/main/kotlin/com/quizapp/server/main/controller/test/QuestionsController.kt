package com.quizapp.server.main.controller.test

import com.quizapp.server.main.models.user.Question
import com.quizapp.server.main.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/users/questions")
class QuestionController {

    @Autowired
    private lateinit var questionService: QuestionService

    @PostMapping("/create")
    fun createQuestion(@RequestBody question: Question): ResponseEntity<Question> {
        val createdQuestion = questionService.createQuestion(question)
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