package com.skillmapshare.api.domain.model.email

import com.skillmapshare.api.domain.model.users.Email
import org.hibernate.sql.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine


open class MailTemplate(
        val mailTo : Email,
        val mailFrom : Email,
        val mailCc : Email?,
        val mailBcc : Email,
        val subject : String,
        val mailBody : String,
) {}