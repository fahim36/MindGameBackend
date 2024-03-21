package com.quizapp.server.main.exceptions

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GenericExceptionHandler {

    data class ErrorResponse(val status: HttpStatus,val message: String)
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        val problemDetail : ErrorResponse? = when (ex) {
            is HttpMessageNotReadableException -> {
                ex.localizedMessage?.let { ErrorResponse(HttpStatus.BAD_REQUEST, it) }
            }
            is ExpiredJwtException -> {
                ex.localizedMessage?.let { ErrorResponse(HttpStatus.FORBIDDEN, "JWT expired") }
            }
            else -> {
                ex.message?.let { ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, it) }
            }
        }
        return ResponseEntity(problemDetail, HttpStatus.OK)
    }

}
