package com.skillmapshare.api.infra.mailer

import com.skillmapshare.api.domain.infra.mailer.MailerInterface
import com.skillmapshare.api.domain.model.email.MailTemplate
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("test")
@Component
class MockMailer : MailerInterface {
    override fun send(mailTemplate: MailTemplate) {
        println("メール送信しました")
    }


}