package com.quizapp.server.main.models.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Topic(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val topicId: Long? = null,
    val topicName: String
)

@Entity
data class Level(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val levelId: Long? = null,
    val topicId: Long? = null,
    val levelName: String
)

@Entity
data class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val questionId: Long? = null,
    val questionTextImg: String,
    val questionOptionsImg: List<String>,
    val topicId: String,
    val levelId: String,
    val correctAnswer: String
)

@Entity
data class TestResult(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val resultId: Long? = null,
    val userId: String,
    val score: Int
)

@Entity
data class Leaderboard(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val leaderboardId: Long? = null,
    val userId: String,
    val topicId: String,
    val levelId: String,
    val score: Int
)