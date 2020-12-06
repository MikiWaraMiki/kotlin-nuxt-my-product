package com.skillmapshare.api.util.helper

import org.springframework.core.io.ClassPathResource
import java.io.File

class FileStreamHelper {
  companion object {
    fun generateFileStream(filePath : String) : ByteArray {
      val concatFilePath = "fixtures/file/$filePath"
      val resource = ClassPathResource(concatFilePath)
      val stream = resource.inputStream
      return stream.readAllBytes()
    }
  }
}
