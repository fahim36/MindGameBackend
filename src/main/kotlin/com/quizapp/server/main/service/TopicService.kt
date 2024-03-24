package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.Topic
import com.quizapp.server.main.repository.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TopicService {

    @Autowired
    private lateinit var topicRepository: TopicRepository

    fun createTopic(topic: Topic): Topic {
        return topicRepository.save(topic)
    }

    fun deleteTopicById(topicId: Long) {
        topicRepository.deleteById(topicId)
    }
}