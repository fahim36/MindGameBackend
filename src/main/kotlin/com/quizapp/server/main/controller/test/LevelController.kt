package com.quizapp.server.main.controller.test

import com.quizapp.server.main.models.user.Level
import com.quizapp.server.main.service.LevelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/levels")
class LevelController {

    @Autowired
    private lateinit var levelService: LevelService

    @PostMapping("/create")
    fun createLevel(@RequestBody level: Level): ResponseEntity<Level> {
        val createdLevel = levelService.createLevel(level)
        return ResponseEntity.ok(createdLevel)
    }

    @DeleteMapping("/delete/{levelId}")
    fun deleteLevel(@PathVariable levelId: Long): ResponseEntity<String> {
        levelService.deleteLevelById(levelId)
        return ResponseEntity.ok("Level with ID $levelId deleted successfully")
    }
}