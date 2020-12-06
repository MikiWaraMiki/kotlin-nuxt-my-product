package com.skillmapshare.api.domain.infra.mailer

import com.skillmapshare.api.domain.model.email.MailTemplate
import org.springframework.scheduling.annotation.Async

interface MailerInterface {
    @Async
    fun send(mailTemplate: MailTemplate)
}