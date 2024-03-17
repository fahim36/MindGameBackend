package com.quizapp.server.main.controller.balloon

import com.quizapp.server.main.models.user.balloon.Balloon
import com.quizapp.server.main.service.BalloonService
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@RestController
@RequestMapping("/api/users/balloon")
class BalloonController(private val balloonService: BalloonService) {

    private val imagePath = "D:\\Projects\\MindGameBackend\\image_path"

    @PostMapping
    fun createBalloon(@RequestParam("image") image: MultipartFile, @RequestParam("shuffleNumber") shuffleNumber: Int): Balloon {
        val imagePath = saveImageToLocal(image)
        val balloon = Balloon(id = UUID.randomUUID(), shuffleNumber = shuffleNumber, balloonImage = imagePath)
        return balloonService.saveBalloon(balloon)
    }
    @GetMapping("/{id}")
    fun getBalloonById(@PathVariable id: UUID): ResponseEntity<Balloon> {
        val balloon = balloonService.getBalloonById(id)
        return ResponseEntity.ok(balloon)
    }
    @GetMapping("getBalloonImageById/{id}")
    fun getBalloonImageById(@PathVariable id: UUID): ResponseEntity<Resource> {
        val balloon = balloonService.getBalloonById(id)
        val file = File(balloon.balloonImage)
        val resource = ByteArrayResource(file.readBytes())

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name}")
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource)
    }

    private fun saveImageToLocal(image: MultipartFile): String {
        val imagePath = imagePath + "\\${UUID.randomUUID()}"
        val file = File(imagePath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        image.transferTo(file)
        return imagePath
    }
}