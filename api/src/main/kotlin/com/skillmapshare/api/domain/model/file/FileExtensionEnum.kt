package com.skillmapshare.api.domain.model.file

import org.springframework.util.MimeTypeUtils

enum class FileExtensionEnum(val contentType : String) {
  JPEG(MimeTypeUtils.IMAGE_JPEG_VALUE) {
    override fun getExtension() : String {
      return ".jpg"
    }
  },
  PNG(MimeTypeUtils.IMAGE_PNG_VALUE) {
    override fun getExtension() : String {
      return ".png"
    }
  },
  GIF(MimeTypeUtils.IMAGE_GIF_VALUE) {
    override  fun getExtension() : String {
      return ".gif"
    }
  },
  OTHER(MimeTypeUtils.ALL_VALUE) {
    override fun getExtension(): String {
      return ""
    }
  }
  ;

  abstract fun getExtension() : String

  companion object {
    fun getExtensionFromContentType(contentType: String) : String {
      val findExtensionResult = values().firstOrNull {
        it.contentType == contentType
      } ?: OTHER

      return findExtensionResult.getExtension()
    }
  }
}
