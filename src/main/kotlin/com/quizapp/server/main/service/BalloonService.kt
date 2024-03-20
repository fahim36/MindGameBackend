package com.quizapp.server.main.service

import com.quizapp.server.main.models.user.balloon.Balloon
import com.quizapp.server.main.repository.BalloonRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class BalloonService(private val balloonRepository: BalloonRepository) {
    fun saveBalloon(balloon: Balloon): Balloon {
        return balloonRepository.save(balloon)
    }

    fun getBalloonById(id: UUID): Balloon {
        return balloonRepository.findById(id).orElseThrow { NoSuchElementException("Balloon not found") }
    }

    fun getAllBalloon() : List<Balloon>{
        return balloonRepository.findAll().toList()
    }

    fun updateBalloon(balloon: Balloon): Balloon {
        return balloonRepository.save(balloon)
    }

    fun deleteBalloonById(id: UUID) {
        balloonRepository.deleteById(id)
    }
}