package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.TestResult
import com.quizapp.server.main.repository.ResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResultService {

    @Autowired
    private lateinit var resultRepository: ResultRepository

    fun calculateAndStoreScore(result: TestResult):TestResult {
        // Implement score calculation logic based on the test scoring system
        // Once calculated, store the result in the database
        return resultRepository.save(result)
    }
}