package com.skillmapshare.api.domain.model.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EmailTests {

  @Nested
  /**
   * [com.skillmapshare.api.domain.model.users.Email] コンストラクタテスト
   */
  inner class ConstructorTest {
    @Test
      /**
       * 異常系 メールアドレスが入力されていない場合はエラー
       */
    fun failed_Constructor_If_Value_Is_Empty() {
      val error = assertThrows<IllegalArgumentException> {
        Email("")
      }
      Assertions.assertEquals("メールアドレスの入力は必須です", error.message)
    }

    @Test
      /**
       * 異常系　メールアドレスの形式が不正な場合はエラー
       */
    fun failed_Constructor_If_Value_IsEmpty() {
      val error = assertThrows<IllegalArgumentException> {
        Email("hogehogehoge")
      }
      Assertions.assertEquals("メールアドレスの形式が正しくありません", error.message)
    }

    @Test
      /**
       * 正常系　形式が正しければオブジェクトを生成できる
       */
    fun success_Constructor_If_Valid_Value() {
      val email = Email("hoge@example.com")
      Assertions.assertEquals("hoge@example.com", email.value)
    }
  }
}
