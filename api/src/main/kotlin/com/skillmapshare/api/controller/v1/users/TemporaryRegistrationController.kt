package com.skillmapshare.api.controller.v1.users

import com.skillmapshare.api.application.usecase.users.temporaryregistration.TemporaryRegistrationInputCommand
import com.skillmapshare.api.application.usecase.users.temporaryregistration.TemporaryRegistrationResultDto
import com.skillmapshare.api.application.usecase.users.temporaryregistration.TemporaryRegistrationService
import com.skillmapshare.api.domain.infra.mailer.MailerInterface
import com.skillmapshare.api.domain.infra.repository.users.TemporaryAccountRepositoryInterFace
import com.skillmapshare.api.infra.mailer.SmtpMailer
import com.skillmapshare.api.infra.mysql.repository.users.TemporaryAccountRepository
import org.hibernate.sql.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*
import org.thymeleaf.TemplateEngine

@RestController
/**
 * 仮登録処理に関わるコントローラ
 */
class TemporaryRegistrationController(
        private val templateEngine: TemplateEngine,
        private val javaMailSender: JavaMailSender,
        private val mailer : MailerInterface,
        private val repository : TemporaryAccountRepositoryInterFace,
        private val usecase : TemporaryRegistrationService
) {

    @GetMapping("/api/v1/test")
    @ResponseStatus(HttpStatus.OK)
    fun test() : Map<String, String> {
      val response = HashMap<String, String>()
      response["response"] = "test"
      return response
    }
    @PostMapping("/api/v1/users/temporary_registration")
    @ResponseStatus(HttpStatus.CREATED)
            /**
             * 仮登録処理を行うメソッド
             * <p> path: /api/v1/users/temporary_registration
             */
    fun create(
            @RequestBody command : TemporaryRegistrationInputCommand
    ) : TemporaryRegistrationResultDto {
        val resultDto = usecase.registration(command)

        return resultDto
    }
}
