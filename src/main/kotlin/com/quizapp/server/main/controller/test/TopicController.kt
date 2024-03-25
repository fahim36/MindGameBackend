package com.quizapp.server.main.controller.test

import com.quizapp.server.main.models.user.Topic
import com.quizapp.server.main.service.TopicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/topics")
class TopicController {

    @Autowired
    private lateinit var topicService: TopicService

    @PostMapping("/create")
    fun createTopic(@RequestBody topic: Topic): ResponseEntity<Topic> {
        val createdTopic = topicService.createTopic(topic)
        return ResponseEntity.ok(createdTopic)
    }

    @DeleteMapping("/delete/{topicId}")
    fun deleteTopic(@PathVariable topicId: Long): ResponseEntity<String> {
        topicService.deleteTopicById(topicId)
        return ResponseEntity.ok("Topic with ID $topicId deleted successfully")
    }
}