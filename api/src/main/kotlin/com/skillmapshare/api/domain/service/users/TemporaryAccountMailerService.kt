package com.skillmapshare.api.domain.service.users

import com.skillmapshare.api.domain.infra.mailer.MailerInterface
import com.skillmapshare.api.domain.model.users.RegistrationUrlNotifyMail
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

class TemporaryAccountMailerService(
        private val mailer : MailerInterface,
        private val templateEngine : TemplateEngine
) {
    fun sendEmail(temporaryAccount : TemporaryAccount) {
        val mailTemplate = RegistrationUrlNotifyMail(
                temporaryAccount,
                generateMailBody(temporaryAccount)
        )


        mailer.send(mailTemplate)
    }

    private fun generateMailBody(temporaryAccount: TemporaryAccount) : String {
        val ctx = Context()
        ctx.setVariable("email", temporaryAccount.email.value)
        println(ctx.getVariable("email"))
        return templateEngine.process(
                RegistrationUrlNotifyMail.TEMPLATE_PATH,
                ctx
        )
    }

}