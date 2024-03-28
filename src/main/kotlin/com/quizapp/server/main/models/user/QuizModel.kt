package com.quizapp.server.main.models.user

import io.jsonwebtoken.lang.Arrays
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
    @Lob // Use @Lob annotation for large objects
    @Column(nullable = false, length = 10485760, columnDefinition = "Text")
    val questionTextImg: String,
    @Convert(converter = StringListConverter::class)
    @Column(name = "questionOptionsImg", nullable = false, columnDefinition = "string-array")
    private val questionOptionsImg: List<String> = ArrayList(),
    val topicId: Long,
    val levelId: Long,
    val category: String,
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


@Converter
class StringListConverter : AttributeConverter<List<String?>?, String?> {
    override fun convertToDatabaseColumn(stringList: List<String?>?): String? {
        return if (stringList != null) java.lang.String.join(SPLIT_CHAR, stringList) else ""
    }

    override fun convertToEntityAttribute(string: String?): List<String?>? {
        return if (string != null) Arrays.asList(string.split(SPLIT_CHAR.toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()) else emptyList()
    }

    companion object {
        private const val SPLIT_CHAR = ";"
    }
}