package com.skillmapshare.api.infra.mailer.smtp

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetup
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.skillmapshare.api.domain.model.email.MailTemplate
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.infra.mailer.SmtpMailer
import org.aspectj.lang.annotation.Before
import org.hibernate.sql.Template
import org.junit.AfterClass
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.context.event.annotation.BeforeTestClass
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.lang.Exception
import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.jvm.Throws

@SpringBootTest
@DisplayName("SMTPメール送信クラスのテスト")
/**
 * [com.skillmapshare.api.infra.mailer.SmtpMailer]のテスト
 */
class SmtpMailerTest {
    @Autowired
    private lateinit var templateEngine : TemplateEngine
    private lateinit var javaMailerMock : JavaMailSender
    private lateinit var smtpMailer : SmtpMailer


    private val mailAddress = Email("test@example.com")
    private lateinit var  mailTemplate : MailTemplate

    companion object {
        private val greenMail = GreenMail(
                ServerSetup(3025, "localhost", "smtp")
        )
        private lateinit var mimeMessage: MimeMessage
        @BeforeAll
        @JvmStatic
        @Throws(Exception::class)
        fun setUpBeforeClass() {
            val properties = Properties()
            properties["mail.smtp.host"] = "localhost"
            properties["mail.smtp.port"] = "3025"
            val session = Session.getDefaultInstance(properties)
            mimeMessage = MimeMessage(session)
            greenMail.start()
        }

        @AfterAll
        @JvmStatic
        @Throws(Exception::class)
        fun afterClass() {
            greenMail.stop()
        }
    }

    @BeforeEach
    fun setUp() {
        mailTemplate = MailTemplate(
                mailTo = mailAddress,
                mailBcc = mailAddress,
                mailFrom = mailAddress,
                mailCc = mailAddress,
                mailBody = "body",
                subject = "test"
        )
        javaMailerMock = mock<JavaMailSender> {
            on { createMimeMessage() } doReturn mimeMessage
        }

        smtpMailer = SmtpMailer(javaMailerMock)
    }

    @Test
    @DisplayName("メールが送信されること")
    fun send_A_Email() {
        smtpMailer.send(mailTemplate)

        println(greenMail.receivedMessages.size)
        verify(javaMailerMock).send(any<MimeMessage>())
        // [Todo] Mail送信結果のテストコードを拡充する
        /*val sendedMail = greenMail.receivedMessages.get(0)
        Assertions.assertEquals(
                mailAddress.value,
                sendedMail.from[0].toString()
        )
        Assertions.assertEquals(
                mailAddress.value,
                sendedMail.getRecipients(Message.RecipientType.TO)[0].toString()
        )
        Assertions.assertEquals(
                "test",
                sendedMail.subject
        )*/
    }
}