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
@RequestMapping("api/user")
class RegistrationController(private val userService: UserService) {


    @PostMapping
    fun create(@RequestBody userRequest: RegRequest): RegResponse {
        return userService.createUser(userRequest.toModel())?.toResponse()
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot Create a User")
    }

    @GetMapping
    fun listAll(): List<RegResponse> {
        return userService.findAll().map { it.toResponse() }
    }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID): RegResponse {
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
    return User(UUID.randomUUID(), password, username, email, role,isColorBlind ,hasLeaderBoardPermission)
}

fun User.toResponse(): RegResponse {
    return RegResponse(id, username, role, email , hasLeaderBoard_permission , isColorBlind )
}