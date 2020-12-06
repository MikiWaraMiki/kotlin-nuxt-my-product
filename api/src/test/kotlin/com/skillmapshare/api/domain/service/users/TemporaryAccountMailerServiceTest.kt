package com.skillmapshare.api.domain.service.users

import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import com.skillmapshare.api.infra.mailer.MockMailer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.thymeleaf.TemplateEngine

@SpringBootTest
@DisplayName("仮登録完了メール送信クラスの単体テスト")
/**
 * [com.skillmapshare.api.domain.service.users.TemporaryAccountMailerService]
 */
class TemporaryAccountMailerServiceTest {
    @Autowired
    private lateinit var templateEngine: TemplateEngine

    private val temporaryAccount = TemporaryAccount(
            _email = Email("test@example.com")
    )
    private val mailer = MockMailer()
    private lateinit var mailerService : TemporaryAccountMailerService

    @BeforeEach
    fun setUp() {
        mailerService = TemporaryAccountMailerService(mailer, templateEngine)
    }
    @Test
    @DisplayName("処理が正常に完了すること")
    fun result_Is_Success() {
        try {
            mailerService.sendEmail(temporaryAccount)
        } catch (e : Exception) {
            fail("メール送信の前に例外が発生しました")
        }
    }
}