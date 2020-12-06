package com.skillmapshare.api.infra.mailer

import com.skillmapshare.api.domain.model.email.MailTemplate
import com.skillmapshare.api.domain.infra.mailer.MailerInterface
import org.springframework.context.annotation.Profile
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import javax.mail.Transport
import kotlin.jvm.Throws

@Component
class SmtpMailer(
        private val mailSender : JavaMailSender,
) : MailerInterface {
    @Async
    @Throws(MailException::class)
    override fun send(mailTemplate: MailTemplate) {
        val mimeMessage = mailSender.createMimeMessage()
        val message = MimeMessageHelper(mimeMessage, true, "UTF-8")
        message.setSubject(mailTemplate.subject)
        message.setFrom(mailTemplate.mailFrom.value)
        message.setTo(mailTemplate.mailTo.value)
        message.setBcc(mailTemplate.mailBcc.value)
        if ( mailTemplate.mailCc != null) message.setCc(mailTemplate.mailCc.value)

        message.setText(mailTemplate.mailBody, true)

        println("start email send")
        mailSender.send(mimeMessage)
        println("finish email send")

    }
}