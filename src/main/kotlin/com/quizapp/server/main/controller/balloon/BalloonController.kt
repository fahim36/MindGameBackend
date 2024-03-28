package com.quizapp.server.main.controller.balloon

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.StorageOptions
import com.quizapp.server.main.Utils.uploadImageToFirebaseStorage
import com.quizapp.server.main.models.user.balloon.Balloon
import com.quizapp.server.main.service.BalloonService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/api/users/balloon")
class BalloonController(private val balloonService: BalloonService) {

    @PostMapping("/createBalloon")
    fun createBalloon(@RequestParam("image") image: MultipartFile, @RequestParam("shuffleNumber") shuffleNumber: Int): Balloon {
        val imageUrl = uploadImageToFirebaseStorage(image)
        val balloon = Balloon(shuffleNumber = shuffleNumber, balloonImage = imageUrl)
        return balloonService.saveBalloon(balloon)
    }



    @GetMapping("/getAllBalloon")
    fun getBalloonById(): ResponseEntity<List<Balloon>> {
        val balloon = balloonService.getAllBalloon()
        return ResponseEntity.ok(balloon)
    }
    @GetMapping("getBalloonImageById/{id}")
    fun getBalloonImageById(@PathVariable id: Long): ResponseEntity<Resource> {
        val balloon = balloonService.getBalloonById(id)
        val file = File(balloon.balloonImage)
        val resource = ByteArrayResource(file.readBytes())

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name}")
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource)
    }
}