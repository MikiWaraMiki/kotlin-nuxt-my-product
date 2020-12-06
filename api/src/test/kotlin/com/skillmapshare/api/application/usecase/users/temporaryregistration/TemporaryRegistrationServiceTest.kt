package com.skillmapshare.api.application.usecase.users.temporaryregistration

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.skillmapshare.api.application.exception.ErrorStatusEnum
import com.skillmapshare.api.application.exception.UserOperationException
import com.skillmapshare.api.domain.infra.mailer.MailerInterface
import com.skillmapshare.api.domain.infra.repository.users.TemporaryAccountRepositoryInterFace
import com.skillmapshare.api.domain.model.email.MailTemplate
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import com.skillmapshare.api.infra.mysql.model.users.TemporaryAccountOrm
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.thymeleaf.TemplateEngine

@SpringBootTest
class TemporaryRegistrationServiceTest {
    @Autowired
    private lateinit var templateEngine: TemplateEngine
    private lateinit var mailSenderMapper : MailerInterface
    private lateinit var tempAccountRepositoryMapper : TemporaryAccountRepositoryInterFace
    private lateinit var testUsecase : TemporaryRegistrationService
    private val tempAccount = TemporaryAccount(
            _email = Email("hoge@example.com")
    )
    private val inputCommand = TemporaryRegistrationInputCommand(
            email = tempAccount.email.value
    )

    @BeforeEach
    fun setup() {
        mailSenderMapper = mock<MailerInterface> {}
    }
    @Test
    @DisplayName("登録に成功した場合は、TemporaryRegistrationResultDtoを返す")
    fun return_Dto_If_RegistrationSuccess() {
        tempAccountRepositoryMapper = mock<TemporaryAccountRepositoryInterFace> {
            on { findByEmail(any<Email>()) } doReturn null
            on { save(any<TemporaryAccount>()) } doReturn  TemporaryAccountOrm(
                    id = 1,
                    email = tempAccount.email.value,
                    emailVerificationToken = tempAccount.emailVerificationToken.token,
                    tokenExpiredDatetime = tempAccount.tokenExpiredDatetime
            )
        }
        testUsecase = TemporaryRegistrationService(
                tempAccountRepositoryMapper,
                mailSenderMapper,
                templateEngine
        )

        val result = testUsecase.registration(inputCommand)

        Assertions.assertNotNull(result)
        Assertions.assertNotNull(result.id)
        Assertions.assertEquals(result.email, inputCommand.email)
    }

    @Test
    @DisplayName("すでに登録済の場合は、UserOperationExceptionが発生すること")
    fun raise_UserOperationException_If_User_Already_Exist() {
        tempAccountRepositoryMapper = mock<TemporaryAccountRepositoryInterFace> {
            on { findByEmail(any<Email>()) } doReturn TemporaryAccountOrm(
                    email = tempAccount.email.value,
                    emailVerificationToken = tempAccount.emailVerificationToken.token,
                    tokenExpiredDatetime = tempAccount.tokenExpiredDatetime
            )
        }
        testUsecase = TemporaryRegistrationService(
                tempAccountRepositoryMapper,
                mailSenderMapper,
                templateEngine
        )
        val error = assertThrows<UserOperationException> {
            testUsecase.registration(inputCommand)
        }

        Assertions.assertEquals(400, error.getErrorStatusCode())
        Assertions.assertEquals("すでに仮登録済です", error.message)
    }

    @Test
    @DisplayName("メールアドレスが未入力の場合は、UserOperationExceptionが発生すること")
    fun raise_UserOperationException_If_Email_Empty_String() {
        val invalidCommand = TemporaryRegistrationInputCommand(
                email = ""
        )
        tempAccountRepositoryMapper = mock<TemporaryAccountRepositoryInterFace> {}
        testUsecase = TemporaryRegistrationService(
                tempAccountRepositoryMapper,
                mailSenderMapper,
                templateEngine
        )

        val error = assertThrows<UserOperationException> {
            testUsecase.registration(invalidCommand)
        }

        Assertions.assertEquals(422, error.getErrorStatusCode())
    }
}