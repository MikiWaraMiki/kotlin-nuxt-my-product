package com.skillmapshare.api.domain.model.users

import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("UserDisplayNameの単体テスト")
/**
 * [com.skillmapshare.api.domain.model.users.UserDisplayName]
 */
class UserDisplayNameTests {

  @Nested
  @DisplayName("コンストラクタのテスト")
  inner class ConstructorTest {
    @Test
    @DisplayName("表示名が空の場合はエラーになること")
    fun failed_Constructor_If_Value_Is_Empty() {
      val error = assertThrows<IllegalArgumentException> {
        UserDisplayName("")
      }
      Assertions.assertEquals("表示名の入力は必須です", error.message)
    }

    @Test
    @DisplayName("表示名が51文字以上の場合はエラーになること")
    fun failed_Constructor_If_Value_Is_Over() {
      val error = assertThrows<IllegalArgumentException> {
        UserDisplayName("a".repeat(51))
      }
      Assertions.assertEquals("表示名は50文字以内で入力してください", error.message)
    }
  }
}
