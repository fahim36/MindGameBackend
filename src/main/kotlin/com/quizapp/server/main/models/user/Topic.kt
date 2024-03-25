package com.quizapp.server.main.models.user

import jakarta.persistence.*

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
    @ElementCollection
    val questionOptionsImg : List<String>,
    val topicId: Long,
    val levelId: Long,
    val correctAnswer: String
)

@Entity
data class TestResult(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val resultId: Long? = null,
    val userId: Long,
    val score: Int
)

@Entity
data class Leaderboard(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val leaderboardId: Long? = null,
    val userId: Long,
    val topicId: Long,
    val levelId: Long,
    val score: Int
)