package com.quizapp.server.main.repository.user

import com.quizapp.server.main.models.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CrudRepository<User,UUID> {
    fun findByUsername(username : String) : User?
}

