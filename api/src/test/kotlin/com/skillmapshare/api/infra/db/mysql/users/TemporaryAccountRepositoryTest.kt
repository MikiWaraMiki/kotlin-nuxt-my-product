package com.skillmapshare.api.infra.db.mysql.users

import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import com.skillmapshare.api.infra.mysql.model.users.TemporaryAccountOrm
import com.skillmapshare.api.infra.mysql.repository.users.TemporaryAccountRepository
import com.skillmapshare.api.util.annotation.JpaTestAnnotationMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@JpaTestAnnotationMapper
@Import(TemporaryAccountRepository::class)
class TemporaryAccountRepositoryTest {
    @Autowired
    private lateinit var testEntityManager: TestEntityManager
    @Autowired
    private lateinit var temporaryAccountRepository: TemporaryAccountRepository

    @Nested
    @JpaTestAnnotationMapper
    @Import(TemporaryAccountRepository::class)
    inner class FindByEmailTest {
        private lateinit var tempAccount : TemporaryAccount
        @BeforeEach
        fun setup() {
            tempAccount = TemporaryAccount(
                    _email = Email("hoge@example.com")
            )
            testEntityManager.persist(
                    TemporaryAccountOrm(
                            email = tempAccount.email.value,
                            emailVerificationToken = tempAccount.emailVerificationToken.token,
                            tokenExpiredDatetime = tempAccount.tokenExpiredDatetime
                    )
            )
        }
        @Test
        @DisplayName("存在する場合はTemporaryAccountOrmを返すこと")
        fun return_Object_If_Email_Exists() {
            val result = temporaryAccountRepository.findByEmail(
                    tempAccount.email
            )

            Assertions.assertNotNull(result)
            if (result != null) {
                Assertions.assertEquals(tempAccount.email.value, result.email)
            }
        }
        @Test
        @DisplayName("存在しない場合はNullを返すこと")
        fun return_Null_If_Email_Not_Exists() {
            val result = temporaryAccountRepository.findByEmail(
                    Email("fuga@example.com")
            )
            Assertions.assertNull(result)
        }
    }

    @Nested
    @JpaTestAnnotationMapper
    @Import(TemporaryAccountRepository::class)
    inner class SaveTest {
        private lateinit var tempAccount : TemporaryAccount
        @BeforeEach
        fun setup() {
            tempAccount = TemporaryAccount(
                    _email = Email("hoge@example.com")
            )
        }

        @Test
        @DisplayName("データ登録が成功した場合は、TemporaryAccountOrmを返却すること")
        fun return_Object_If_Save_Is_Success() {
            val result= temporaryAccountRepository.save(tempAccount)

            Assertions.assertNotNull(result)
            Assertions.assertNotNull(result.id)
            Assertions.assertEquals(tempAccount.email.value, result.email)
        }
    }
}