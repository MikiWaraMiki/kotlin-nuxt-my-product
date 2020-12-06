package com.skillmapshare.api.application.usecase.users.temporaryregistration

import com.skillmapshare.api.application.exception.ErrorStatusEnum
import com.skillmapshare.api.application.exception.InternalException
import com.skillmapshare.api.application.exception.UserOperationException
import com.skillmapshare.api.domain.infra.mailer.MailerInterface
import com.skillmapshare.api.domain.infra.repository.users.TemporaryAccountRepositoryInterFace
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.RegistrationUrlNotifyMail
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import com.skillmapshare.api.domain.service.users.TemporaryAccountMailerService
import com.skillmapshare.api.domain.service.users.TemporaryAccountService
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine

@Service
class TemporaryRegistrationService(
        private val temporaryAccountRepository : TemporaryAccountRepositoryInterFace,
        private val mailer : MailerInterface,
        private val templateEngine: TemplateEngine
) {
    private val temporaryAccountService = TemporaryAccountService(
            repository = temporaryAccountRepository
    )

    private val temporaryAccountMailerService = TemporaryAccountMailerService(
            mailer = mailer,
            templateEngine = templateEngine
    )

    fun registration(command : TemporaryRegistrationInputCommand) : TemporaryRegistrationResultDto {
        val tempAccount = try {
            TemporaryAccount(_email = Email(command.email))
        } catch(e : IllegalArgumentException) {
            throw UserOperationException(
                    msg = e.message,
                    errorStatus = ErrorStatusEnum.INPUT_PARAMETER_IS_INVALID
            )
        }

        if (temporaryAccountService.exists(tempAccount)) {
            throw UserOperationException(
                    msg = "すでに仮登録済です",
                    errorStatus = ErrorStatusEnum.ALREADY_EXIST_DATA
            )
        }

        val result = temporaryAccountRepository.save(temporaryAccount = tempAccount)

        if (result.id == null) {
            throw InternalException("[Error] 仮登録のリポジトリでsaveしましたが、idが保存されていませんでした")
        }

        temporaryAccountMailerService.sendEmail(tempAccount)

        return TemporaryRegistrationResultDto(
                id = result.id,
                email = result.email
        )
    }
}