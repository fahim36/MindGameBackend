package com.quizapp.server.main.service

import com.quizapp.server.main.repository.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

typealias ApplicationUser = com.quizapp.server.main.models.user.User

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)?.mapToUserDetails() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a User")
    }
    fun getUserDetailsByUserName(userName:String) : com.quizapp.server.main.models.user.User {
        return userRepository.findByUsername(userName)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a User")
    }
}

private fun ApplicationUser.mapToUserDetails(): UserDetails {
    return org.springframework.security.core.userdetails.User.builder().username(username).password(password).roles(role.name).build()
}
