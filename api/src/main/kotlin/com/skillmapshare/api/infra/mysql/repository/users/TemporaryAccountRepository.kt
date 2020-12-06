package com.skillmapshare.api.infra.mysql.repository.users

import com.skillmapshare.api.domain.infra.repository.users.TemporaryAccountRepositoryInterFace
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import com.skillmapshare.api.infra.mysql.model.users.TemporaryAccountOrm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class TemporaryAccountRepository(
        private val jpaRepository: JpaTemporaryAccountRepository
) : TemporaryAccountRepositoryInterFace {
    /**
     * 仮登録ユーザのレコードを１件保存する
     */
    override fun save(temporaryAccount: TemporaryAccount) : TemporaryAccountOrm {
        val temporaryAccountOrm = TemporaryAccountOrm(
                email = temporaryAccount.email.value,
                emailVerificationToken = temporaryAccount.emailVerificationToken.token,
                tokenExpiredDatetime = temporaryAccount.tokenExpiredDatetime
        )
        return jpaRepository.save(temporaryAccountOrm)
    }

    /**
     * Emailの検索
     */
    override fun findByEmail(email: Email) : TemporaryAccountOrm? {

        return jpaRepository.findByEmail(email.value)
    }

}