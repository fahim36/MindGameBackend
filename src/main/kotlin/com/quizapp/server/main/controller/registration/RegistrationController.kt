package com.quizapp.server.main.controller.registration

import com.quizapp.server.main.models.user.User
import com.quizapp.server.main.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("api/register")
class RegistrationController(private val userService: UserService) {
    @PostMapping
    fun create(@RequestBody userRequest: RegRequest): SignUpResponse {

        return if (userService.findByUserName(userRequest.username))
            SignUpResponse(HttpStatus.BAD_REQUEST, "UserName already Exists")
        else if (userService.findByEmail(userRequest.email))
            SignUpResponse(HttpStatus.BAD_REQUEST, "Email already Exists")
        else {
            val user = userRequest.toModel()
            val result = userService.createUser(user)
            if(result!=null)
                SignUpResponse(HttpStatus.OK, "Successfully created user", user.toResponse())
            else
              SignUpResponse(HttpStatus.BAD_REQUEST, "Something Went Wrong.")

        }
    }

    @GetMapping
    fun listAll(): List<RegisteredUserInfo> {
        return userService.findAll().map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun findByUUID(@PathVariable id: Long): RegisteredUserInfo {
        return userService.findById(id)?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a User")
    }

    @DeleteMapping("/{id}")
    fun deleteByUUID(@PathVariable id: Long): ResponseEntity<Boolean> {
        val success = userService.deleteById(id)

        return if (success) {
            ResponseEntity.noContent().build()
        } else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a User")
    }

}

private fun RegRequest.toModel(): User {
    return User(password = password, username = username, email = email, role = role, isColorBlind =  isColorBlind, hasLeaderBoard_permission =  hasLeaderBoardPermission)
}

fun User.toResponse(): RegisteredUserInfo {
    return RegisteredUserInfo(id, username, role, email, hasLeaderBoard_permission, isColorBlind)
}