package com.quizapp.server.main.controller.registration

import com.quizapp.server.main.models.user.User
import com.quizapp.server.main.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("api/register")
class RegistrationController(private val userService: UserService) {


    @PostMapping
    fun create(@RequestBody userRequest: RegRequest): ResponseEntity<SignUpResponse> {

        return if (userService.findByUserName(userRequest.username))
            ResponseEntity.badRequest().body(SignUpResponse(HttpStatus.BAD_REQUEST, "UserName already Exists"))
        else if (userService.findByEmail(userRequest.email))
            ResponseEntity.badRequest().body(SignUpResponse(HttpStatus.BAD_REQUEST, "Email already Exists"))
        else {
            val user = userRequest.toModel()
            val result = userService.createUser(user)
            if(result!=null)
                ResponseEntity.ok()
                .body(SignUpResponse(HttpStatus.OK, "Successfully created user", user.toResponse()))
            else
                ResponseEntity.badRequest().body(SignUpResponse(HttpStatus.BAD_REQUEST, "Something Went Wrong."))

        }
    }

    @GetMapping
    fun listAll(): List<RegisteredUserInfo> {
        return userService.findAll().map { it.toResponse() }
    }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID): RegisteredUserInfo {
        return userService.findByUUID(uuid)?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a User")
    }

    @DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
        val success = userService.deleteByUUID(uuid)

        return if (success) {
            ResponseEntity.noContent().build()
        } else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a User")
    }

}

private fun RegRequest.toModel(): User {
    return User(UUID.randomUUID(), password, username, email, role, isColorBlind, hasLeaderBoardPermission)
}

fun User.toResponse(): RegisteredUserInfo {
    return RegisteredUserInfo(id, username, role, email, hasLeaderBoard_permission, isColorBlind)
}