package com.quizapp.server.main.models.user.balloon

import jakarta.persistence.*
import java.util.*

@Entity
data class Balloon(
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID,
    @Column(nullable = false)
    val shuffleNumber: Int,
    @Lob // Use @Lob annotation for large objects
    @Column(nullable = false, length = 10485760, columnDefinition = "Text") // Set a large length for the column
    val balloonImage: String
)