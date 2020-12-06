package com.skillmapshare.api.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import java.io.FileInputStream
import java.io.IOException
import kotlin.jvm.Throws

@Configuration
class FirebaseConfig() {

  @Primary
  @Bean
  @Throws(IOException::class)
  fun getFirebaseApp() : FirebaseApp {
    try {
      val resource = ClassPathResource("firebase-credentials.json")
      val serviceAccount = resource.inputStream
      val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://mydddprojectgoandnuxt.firebaseio.com")
        .build()
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options)
      }
      return FirebaseApp.getInstance()
    } catch(e : Exception) {
      println(e.message)
      println(e.stackTrace)
      throw Exception(e)
    }
  }

  @Bean
  fun getFirebaseAuth() : FirebaseAuth {
    return FirebaseAuth.getInstance(getFirebaseApp())
  }
}
