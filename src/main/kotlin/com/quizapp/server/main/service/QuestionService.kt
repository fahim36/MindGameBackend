package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.Question
import com.quizapp.server.main.repository.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuestionService {

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    fun createQuestion(question: Question): Question {
        return questionRepository.save(question)
    }

    fun deleteQuestionById(questionId: Long) {
        questionRepository.deleteById(questionId)
    }

    fun getQuestionsByTopicAndLevel(topicId: Long, levelId: Long): List<Question> {
        return questionRepository.findByLevelIdAndTopicId(topicId, levelId) ?: emptyList()
    }
}