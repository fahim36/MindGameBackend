package com.quizapp.server.main

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.StorageOptions
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.concurrent.TimeUnit

object Utils {
    fun uploadImageToFirebaseStorage(image: MultipartFile): String {
        val credentials = GoogleCredentials.fromStream(File("H:\\Project\\MindGameBackend\\MindGameBackend\\credentials.json").inputStream())
        val storage = StorageOptions.newBuilder().setCredentials(credentials).build().service

        val bucketName = "mindgame-9651c.appspot.com"
        val bucket: Bucket = storage.get(bucketName)

        val blobId = "image_path/${System.currentTimeMillis()}_${image.originalFilename}"
        val blobInfo = bucket.create(blobId, image.bytes)
        val url = blobInfo.signUrl( 100, TimeUnit.DAYS)
        return url.toString()
    }

    fun uploadImageToFirebaseStorage(imageList: List<MultipartFile>): List<String> {
        val credentials = GoogleCredentials.fromStream(File("H:\\Project\\MindGameBackend\\MindGameBackend\\credentials.json").inputStream())
        val storage = StorageOptions.newBuilder().setCredentials(credentials).build().service

        val bucketName = "mindgame-9651c.appspot.com"
        val bucket: Bucket = storage.get(bucketName)
        val imageListString: ArrayList<String> = ArrayList()
        imageList.forEach {image->
            val blobId = "image_path/${System.currentTimeMillis()}_${image.originalFilename}"
            val blobInfo = bucket.create(blobId, image.bytes)
            val url = blobInfo.signUrl(100, TimeUnit.DAYS)
            imageListString.add(url.toString())
        }
        return imageListString.toList()
    }
}