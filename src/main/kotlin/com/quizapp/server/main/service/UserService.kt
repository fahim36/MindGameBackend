package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.User
import com.quizapp.server.main.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val encoder: PasswordEncoder, private val userRepository: UserRepository) {
    fun createUser(user: User): User? {
        val found = userRepository.findByUsername(user.username)
        return if (found == null) {
            userRepository.save(user.copy(password = encoder.encode(user.password)))
            user
        } else null
    }

    fun findByUUID(uuid: UUID): User? {
        return userRepository.findById(uuid).orElse(null)
    }

    fun findAll(): List<User> {
        return userRepository.findAll().toList()
    }

    fun deleteByUUID(uuid: UUID): Boolean {
        if (userRepository.findById(uuid).orElse(null) != null) {
            userRepository.deleteById(uuid)
            return true
        }
        return false
    }

}