package com.quizapp.server.main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication()
class QuizApplication

fun main(args: Array<String>) {
	runApplication<QuizApplication>(*args)
}
