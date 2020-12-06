package com.skillmapshare.api.domain.model.users

import com.skillmapshare.api.domain.model.email.MailTemplate
import com.skillmapshare.api.utils.constant.MailConstant
import org.springframework.stereotype.Component

class RegistrationUrlNotifyMail(temporaryAccount: TemporaryAccount, mailBody : String) : MailTemplate(
        mailTo = temporaryAccount.email,
        mailFrom = Email(MailConstant.NOTIFY_MAIL_FORM),
        mailCc = null,
        mailBcc = Email(MailConstant.NOTIFY_MAIL_CC),
        subject = "本登録URLのご案内",
        mailBody = mailBody
) {
    companion object {
        const val TEMPLATE_PATH = "mail/html/users/temporaryregistration/thanks.html"
    }
}