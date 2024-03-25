package com.quizapp.server.main.repository.user

import com.quizapp.server.main.models.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User,Long> {
    fun findByUsername(username : String) : User?
    fun existsByEmail(email: String) : Boolean
    fun existsByUsername(username: String) : Boolean
}

