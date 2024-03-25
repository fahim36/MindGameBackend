package com.quizapp.server.main.repository

import com.quizapp.server.main.models.user.balloon.Balloon
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BalloonRepository : CrudRepository<Balloon, Long>