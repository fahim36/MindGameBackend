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
    @Column(nullable = false)
    val balloonImage: String
)