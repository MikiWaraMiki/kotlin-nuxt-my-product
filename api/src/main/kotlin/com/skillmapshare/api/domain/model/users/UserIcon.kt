package com.skillmapshare.api.domain.model.users

import com.skillmapshare.api.domain.model.file.ImageFile
import org.springframework.web.multipart.MultipartFile
import java.awt.Image

/**
 * UserのプロフィールIconクラス
 */
class UserIcon(private val _file : MultipartFile, private val _namePrefix : String ) {
  private val directoryPrefix = "/users/icon/"
  var file = ImageFile(
    _file = _file,
    _namePrefix = _namePrefix
  )
}
