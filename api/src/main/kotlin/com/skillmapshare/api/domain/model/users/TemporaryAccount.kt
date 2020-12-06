package com.skillmapshare.api.domain.model.users

import org.springframework.stereotype.Repository
import java.time.LocalDateTime

class TemporaryAccount(_email : Email) {
    var email : Email = _email
        private set
    var emailVerificationToken : ConfirmationToken = ConfirmationToken()
        private set
    var tokenExpiredDatetime : LocalDateTime = generateExpiredDatetime()
        private set
    var createdDatetime : LocalDateTime? = null
        private set
    var updatedDateTime : LocalDateTime? = null

    private val TOKEN_USEABLE_TIME : Long = 6


    /**
     * Tokenの再生成
     */
    fun reGenerateEmailVerificationToken() {
        this.emailVerificationToken = ConfirmationToken()
        this.tokenExpiredDatetime = generateExpiredDatetime()
    }

    /**
     * Tokenの失効時間を生成
     */
    private fun generateExpiredDatetime() : LocalDateTime {
        val now = LocalDateTime.now()
        return now.plusHours(TOKEN_USEABLE_TIME)
    }
}