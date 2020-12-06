package com.skillmapshare.api.domain.infra.repository.users

import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import com.skillmapshare.api.infra.mysql.model.users.TemporaryAccountOrm
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface TemporaryAccountRepositoryInterFace {
    fun save(temporaryAccount: TemporaryAccount) : TemporaryAccountOrm
    fun findByEmail(email : Email) : TemporaryAccountOrm?
}