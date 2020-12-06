package com.skillmapshare.api.domain.model.users

import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserApplicationIdTest {

  @Nested
  @DisplayName("コンストラクタのテスト")
  inner class ConstructorTest {
    @Test
    @DisplayName("ユーザIDが空文字の場合はエラーが発生すること")
    fun failed_Constructor_If_Value_Is_Empty() {
      val error = assertThrows<IllegalArgumentException> {
        UserApplicationId("")
      }
      Assertions.assertEquals("ユーザIDの入力は必須です", error.message)
    }
    @Test
    @DisplayName("ユーザIDが31文字以上入力されている場合はエラーが出る")
    fun failed_Constructor_If_Length_Over_Max() {
      val error = assertThrows<IllegalArgumentException> {
        UserApplicationId("a".repeat(31))
      }
      Assertions.assertEquals("ユーザIDは30文字以内で入力してください", error.message)
    }
    @Test
    @DisplayName("ユーザIDが英数字・アンスコ以外使われている場合はエラーが出る")
    fun failed_Constructor_If_Value_Use_Un_Support_Character() {
      val error = assertThrows<IllegalArgumentException> {
        UserApplicationId("a.")
      }
      Assertions.assertEquals("ユーザIDには、英数字と_のみが利用できます", error.message)
    }
  }
}
