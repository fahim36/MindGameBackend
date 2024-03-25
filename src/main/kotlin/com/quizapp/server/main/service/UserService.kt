package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.User
import com.quizapp.server.main.repository.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val encoder: PasswordEncoder, private val userRepository: UserRepository) {
    fun createUser(user: User): User? {
        val found = userRepository.findByUsername(user.username)
        return if (found == null) {
            userRepository.save(user.copy(password = encoder.encode(user.password)))
            user
        } else null
    }

    fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun findByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
   fun findByUserName(userName: String): Boolean {
        return userRepository.existsByUsername(userName)
    }

    fun findAll(): List<User> {
        return userRepository.findAll().toList()
    }

    fun deleteById(uuid: Long): Boolean {
        if (userRepository.findById(uuid).orElse(null) != null) {
            userRepository.deleteById(uuid)
            return true
        }
        return false
    }

}