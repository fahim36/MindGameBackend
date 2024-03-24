package com.quizapp.server.main.controller.balloon

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.StorageOptions
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

    private val imagePath = "D:\\Projects\\MindGameBackend\\image_path"

    @PostMapping("/createBalloon")
    fun createBalloon(@RequestParam("image") image: MultipartFile, @RequestParam("shuffleNumber") shuffleNumber: Int): Balloon {
        val imageUrl = uploadImageToFirebaseStorage(image)
        val balloon = Balloon(id = UUID.randomUUID(), shuffleNumber = shuffleNumber, balloonImage = imageUrl)
        return balloonService.saveBalloon(balloon)
    }

    fun uploadImageToFirebaseStorage(image: MultipartFile): String {
        val credentials = GoogleCredentials.fromStream(File("D:\\Projects\\MindGameBackend\\credentials.json").inputStream())
        val storage = StorageOptions.newBuilder().setCredentials(credentials).build().service

        val bucketName = "mindgame-9651c.appspot.com"
        val bucket: Bucket = storage.get(bucketName)

        val blobId = "image_path/${System.currentTimeMillis()}_${image.originalFilename}"
        val blobInfo = bucket.create(blobId, image.bytes)
        val url = blobInfo.signUrl( 100, TimeUnit.DAYS)
        return url.toString()
    }

    @GetMapping("/getAllBalloon")
    fun getBalloonById(): ResponseEntity<List<Balloon>> {
        val balloon = balloonService.getAllBalloon()
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
}