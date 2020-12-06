package com.skillmapshare.api.domain.model.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TemporaryAccountTest {

    @Nested
    /**
     * [com.skillmapshare.api.domain.model.users.TemporaryAccount] reGenerateEmailVerificationToken
     * Token再生成のテスト
     */
    inner class TestReGenerateEmailVerificationToken {
        private lateinit var tempAccount : TemporaryAccount
        @BeforeEach
        fun setUp() {
            tempAccount = TemporaryAccount(Email("hoge@exmaple.com"))
        }
        @Test
                /**
                 * Tokenの値が変更されていること
                 */
        fun token_Is_Change() {
            val oldToken = tempAccount.emailVerificationToken.token
            tempAccount.reGenerateEmailVerificationToken()

            Assertions.assertNotEquals(oldToken, tempAccount.emailVerificationToken.token)
        }
        @Test
                /**
                 * Tokenの失効期限が更新されること
                 */
        fun tokenExpiredDatetime_Is_Change() {
            val oldExpiredDatetime = tempAccount.tokenExpiredDatetime
            tempAccount.reGenerateEmailVerificationToken()

            Assertions.assertNotEquals(oldExpiredDatetime, tempAccount.tokenExpiredDatetime)
        }
    }
}