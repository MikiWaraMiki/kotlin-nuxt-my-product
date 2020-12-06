package com.skillmapshare.api.domain.model.users

import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("Uuidの単体テスト")
/**
 * [com.skillmapshare.api.domain.model.users.Uuid]の単体テスト
 */
class UuidTests {
  @Nested
  @DisplayName("コンストラクタのテスト")
  inner class ConstructorTest {
    @Test
    @DisplayName("表示名が空の場合はエラーになること")
    fun failed_Constructor_If_Value_Is_Empty() {
      val error = assertThrows<IllegalArgumentException> {
        Uuid("")
      }
      Assertions.assertEquals("uuidが入力されていません", error.message)
    }
    @Test
    @DisplayName("英数字以外のみが利用されている場合エラーになること")
    fun failed_Constructor_If_User_UnSupported_Character() {
      val error = assertThrows<IllegalArgumentException> {
        Uuid("aaa.")
      }
      Assertions.assertEquals("uuidに使用できない文字が使われています", error.message)
    }
  }
}
