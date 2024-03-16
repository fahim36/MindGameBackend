package com.quizapp.server.main.models.user

import com.quizapp.server.main.repository.user.UserType
import jakarta.annotation.Generated
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
        @Column
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID,

        @Column(nullable = false)
        val password: String,

        @Column(nullable = false, unique = true)
        val username: String,

        @Column(nullable = false, unique = true)
        val email: String,

        @Column(nullable = false)
        @Enumerated(value = EnumType.STRING)
        val role: UserType,

        val isColorBlind: Boolean = true,

        val hasLeaderBoard_permission: Boolean = true,
)