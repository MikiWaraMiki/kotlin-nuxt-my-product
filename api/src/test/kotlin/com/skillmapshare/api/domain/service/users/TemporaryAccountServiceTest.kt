package com.skillmapshare.api.domain.service.users

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.skillmapshare.api.domain.infra.repository.users.TemporaryAccountRepositoryInterFace
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import com.skillmapshare.api.infra.mysql.model.users.TemporaryAccountOrm
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TemporaryAccountServiceTest {
    private lateinit var tempAccountRepositoryMapper : TemporaryAccountRepositoryInterFace
    private lateinit var tempAccountService : TemporaryAccountService
    private var tempAccount = TemporaryAccount(
            _email = Email("hoge@example.com")
    )

    @Nested
    @DisplayName("existsメソッドのテスト")
    inner class ExistTest {
        @Test
        @DisplayName("アカウントが存在している場合はTrueを返す")
        fun return_True_If_AccountExists() {
            tempAccountRepositoryMapper = mock<TemporaryAccountRepositoryInterFace> {
                on { findByEmail(tempAccount.email)} doReturn TemporaryAccountOrm(
                        email = tempAccount.email.value,
                        emailVerificationToken = tempAccount.emailVerificationToken.token,
                        tokenExpiredDatetime = tempAccount.tokenExpiredDatetime
                )
            }
            tempAccountService = TemporaryAccountService(
                    repository = tempAccountRepositoryMapper
            )

            Assertions.assertTrue(tempAccountService.exists(tempAccount))
        }
        @Test
        @DisplayName("アカウントが存在しない場合はFalseを返す")
        fun return_False_If_AccountNotExists() {
            tempAccountRepositoryMapper = mock<TemporaryAccountRepositoryInterFace> {
                on { findByEmail(tempAccount.email) } doReturn null
            }
            tempAccountService = TemporaryAccountService(
                    repository = tempAccountRepositoryMapper
            )

            Assertions.assertFalse(tempAccountService.exists(tempAccount))
        }

    }
}