package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.Leaderboard
import com.quizapp.server.main.models.user.TestResult
import com.quizapp.server.main.repository.LeaderboardRepository
import com.quizapp.server.main.repository.ResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LeaderboardService {

    @Autowired
    private lateinit var leaderboardRepository: LeaderboardRepository
    private lateinit var resultRepository: ResultRepository

    fun createLeaderboardEntry(leaderboardEntry: Leaderboard): Leaderboard {
        return leaderboardRepository.save(leaderboardEntry)
    }

    fun deleteLeaderboardEntryById(leaderboardId: Long) {
        leaderboardRepository.deleteById(leaderboardId)
    }
    fun getTopScoresByTopicAndLevel(topicId: Long, levelId: Long): List<Leaderboard>? {
        return leaderboardRepository.findTop10ByTopicIdAndLevelIdOrderByScoreDesc(topicId,levelId)
    }

    fun getResultsByUserId(userId: Long): TestResult {
            return resultRepository.findByUserId(userId)
    }
}