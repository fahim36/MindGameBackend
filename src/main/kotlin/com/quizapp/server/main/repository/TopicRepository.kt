package com.quizapp.server.main.repository

import com.quizapp.server.main.models.user.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TopicRepository : CrudRepository<Topic, Long>{
    // Method to create a new topic
    fun saveTopic(topic: Topic): Topic

    // Method to delete a topic by topic ID
    fun deleteTopicById(topicId: Long)
}

@Repository
interface LevelRepository : CrudRepository<Level, Long>{
    // Method to create a new level
    fun saveLevel(level: Level): Level

    // Method to delete a level by level ID
    fun deleteLevelById(levelId: Long)
}

@Repository
interface QuestionRepository : CrudRepository<Question, Long>{
    fun saveQuestion(question: Question): Question

    // Method to delete a question by question ID
    fun deleteQuestionById(questionId: Long)

    fun findByLevelIdAndTopicId(levelId: Long, topicId: Long): List<Question>

}

interface LeaderboardRepository : CrudRepository<Leaderboard, Long>{
    // Method to create a new leaderboard entry
    fun saveLeaderboardEntry(leaderboardEntry: Leaderboard): Leaderboard

    // Method to delete a leaderboard entry by leaderboard ID
    fun deleteLeaderboardEntryById(leaderboardId: Long)

    // Method to retrieve the top 10 highest scores by topic or complexity
    fun findTop10ByTopicIdAndLevelIdOrderByScoreDesc(topicId: Long,levelId: Long): List<Leaderboard>



}

interface ResultRepository : CrudRepository<TestResult, Long>{
    fun findByUserId(userId: Long): TestResult
}



