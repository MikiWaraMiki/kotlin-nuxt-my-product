package com.skillmapshare.api.domain.model.file

import com.skillmapshare.api.utils.constant.FileSizeConstant
import org.springframework.util.MimeTypeUtils
import org.springframework.web.multipart.MultipartFile
import java.lang.IllegalArgumentException

/**
 * 画像ファイルクラス
 * @args _file  アップロードするファイル
 * @args _name ファイル名
 */
class ImageFile(_file : MultipartFile, _namePrefix : String) {
  var file : MultipartFile = initializeFile(_file)
    private set
  var name : String = initializeFileName(_namePrefix)
    private set
  /**
   * ファイルの初期化処理
   */
  private fun initializeFile(_file: MultipartFile) : MultipartFile {
    if (isOverMaxByteSize(_file)) {
      throw IllegalArgumentException("ファイルのサイズは10MBまでです")
    }
    if (isNotAllowExtension(_file)) {
      throw IllegalArgumentException("ファイルは、jpeg, png, gifでアップロードしてください")
    }
    return _file
  }

  /**
   * ファイル名の初期化処理
   */
  private fun initializeFileName(_namePrefix : String) : String {
    if (isEmptyFileName(_namePrefix)) {
      throw IllegalArgumentException("ファイル名を指定してください")
    }
    // Content Typeから適した拡張子に変換する
    return createFileName(_namePrefix)
  }

  /**
   * Content Typeから新しいファイル名を生成する
   * @args namePrefix ファイル名(拡張子抜き)
   */
  private fun createFileName(_name: String) : String {
    val contentType = file.contentType ?: ""
    return _name + FileExtensionEnum.getExtensionFromContentType(
      contentType
    )
  }
  /**
   * Fileのコンテントタイプを返す
   */
  /**
   * Fileのサイズチェック
   * 10MB以内のファイルのみを許容する
   */
  private fun isOverMaxByteSize(_file: MultipartFile) : Boolean {
    return _file.size > getMaxFileSize()
  }

  /**
   * Fileの拡張子チェック
   * @return true 許容しないcontent typeの場合
   * @return false 許容するcontent typeの場合
   */
  private fun isNotAllowExtension(_file : MultipartFile) : Boolean {
    val contentType = _file.contentType
    return !getAllowFileContentTypeList().contains(contentType)
  }

  /**
   * ファイル名の空文字チェック
   */
  private fun isEmptyFileName(_name : String) : Boolean {
    return _name == ""
  }
  /**
   * ファイルの最大サイズを返す
   */
  private fun getMaxFileSize() : Int {
    return 10 * FileSizeConstant.MB_SIZE
  }

  /**
   * アップロードできるファイルのcontent typeを返す
   * image/jpeg, image/png, image/gifを許容する
   */
  private fun getAllowFileContentTypeList() : List<String> {
    return listOf(
      MimeTypeUtils.IMAGE_GIF_VALUE,
      MimeTypeUtils.IMAGE_PNG_VALUE,
      MimeTypeUtils.IMAGE_JPEG_VALUE
    )
  }
}
