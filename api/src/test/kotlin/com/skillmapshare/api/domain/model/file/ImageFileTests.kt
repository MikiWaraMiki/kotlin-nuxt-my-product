package com.skillmapshare.api.domain.model.file

import ch.qos.logback.core.util.ContentTypeUtil
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.skillmapshare.api.util.helper.FileStreamHelper
import com.skillmapshare.api.utils.constant.FileSizeConstant
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.util.MimeTypeUtils
import org.springframework.web.multipart.MultipartFile

@SpringBootTest
class ImageFileTests {
  private val mockImgFile = MockMultipartFile(
    "images",
    "jpgImage",
    "image/png",
    FileStreamHelper.generateFileStream("sample_image1.png")
  )

  @Nested
  @DisplayName("初期化処理のテスト")
  inner class InitializeTest {
    @Test
    @DisplayName("ファイルサイズが10MBを超えている場合はエラーになる")
    fun failed_Initialize_When_Size_Is_Over() {
      val file = mock<MultipartFile> {
        on { size } doReturn (FileSizeConstant.MB_SIZE * 11).toLong()
        on { contentType } doReturn MimeTypeUtils.IMAGE_JPEG_VALUE
      }

      val error = assertThrows<IllegalArgumentException> {
        ImageFile(file, "test.jpg")
      }

      Assertions.assertEquals("ファイルのサイズは10MBまでです", error.message)
    }
    @Test
    @DisplayName("ファイルのcontent typeがサポートしていない場合はエラーになる")
    fun failed_Initialize_When_File_ContentType_Un_Support() {
      var file = mock<MultipartFile> {
        on { size } doReturn (FileSizeConstant.MB_SIZE * 10).toLong()
        on { contentType } doReturn MimeTypeUtils.TEXT_PLAIN_VALUE
      }
      val error = assertThrows<IllegalArgumentException> {
        ImageFile(file, "test.jpg")
      }
      Assertions.assertEquals("ファイルは、jpeg, png, gifでアップロードしてください", error.message)
    }
    @Test
    @DisplayName("ファイルの名前が空の場合はエラーになる")
    fun failed_Initialize_When_File_Name_Prefix_Is_Empty() {
      var file = mock<MultipartFile> {
        on { size } doReturn FileSizeConstant.MB_SIZE.toLong()
        on { contentType } doReturn MimeTypeUtils.IMAGE_GIF_VALUE
      }
      var error = assertThrows<IllegalArgumentException> {
        ImageFile(file, "")
      }

      Assertions.assertEquals("ファイル名を指定してください", error.message)
    }
    @Test
    @DisplayName("ファイル名が指定されていて、ContentTypeがjpegの場合は成功する")
    fun success_Initialize_When_jpegFile() {
      val imgFile = ImageFile(
        mockImgFile,
        "user1"
      )

      Assertions.assertNotNull(imgFile)
      Assertions.assertEquals("user1.png", imgFile.name)
    }
  }
}
