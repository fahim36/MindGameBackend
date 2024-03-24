package com.quizapp.server.main.controller.test
import com.quizapp.server.main.models.user.Leaderboard
import com.quizapp.server.main.models.user.TestResult
import com.quizapp.server.main.service.LeaderboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController {

    @Autowired
    private lateinit var leaderboardService: LeaderboardService

    @GetMapping("/topScoresByTopicAndLevel/{topicId}/{levelId}")
    fun getTopScoresByTopicAndLevel(@PathVariable topicId: Long, @PathVariable levelId: Long): ResponseEntity<List<Leaderboard>> {
        val topScores = leaderboardService.getTopScoresByTopicAndLevel(topicId, levelId)
        return ResponseEntity.ok(topScores)
    }

    @PostMapping("/createResult")
    fun createResult(@RequestBody leaderboard: Leaderboard): ResponseEntity<Leaderboard> {
        val createdResult = leaderboardService.createLeaderboardEntry(leaderboard)
        return ResponseEntity.ok(createdResult)
    }
    @GetMapping("/results/{userId}")
    fun getResultsByUserId(@PathVariable userId: Long): ResponseEntity<TestResult> {
        val results = leaderboardService.getResultsByUserId(userId)
        return ResponseEntity.ok(results)
    }
}