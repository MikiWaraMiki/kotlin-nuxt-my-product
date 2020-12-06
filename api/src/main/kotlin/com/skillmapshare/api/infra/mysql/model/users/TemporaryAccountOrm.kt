package com.skillmapshare.api.infra.mysql.model.users

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "temporary_accounts")
data class TemporaryAccountOrm (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
        @Column(name = "email", nullable = false)
    val email: String,
        @Column(name = "email_verification_token", nullable = false)
    val emailVerificationToken : String,
        @Column(name = "token_expired_datetime", nullable = false)
    val tokenExpiredDatetime : LocalDateTime,
        @Column(name = "created_datetime", nullable = false)
    val createdDatetime : LocalDateTime = LocalDateTime.now(),
        @Column(name = "updated_datetime", nullable = false)
    val updatedDateTime : LocalDateTime = LocalDateTime.now()
)
