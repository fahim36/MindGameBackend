package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.Level
import com.quizapp.server.main.repository.LevelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LevelService {

    @Autowired
    private lateinit var levelRepository: LevelRepository

    fun createLevel(level: Level): Level {
        return levelRepository.save(level)
    }

    fun deleteLevelById(levelId: Long) {
        levelRepository.deleteById(levelId)
    }
}