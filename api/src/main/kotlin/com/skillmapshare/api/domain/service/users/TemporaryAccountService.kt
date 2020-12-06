package com.skillmapshare.api.domain.service.users

import com.skillmapshare.api.domain.infra.repository.users.TemporaryAccountRepositoryInterFace
import com.skillmapshare.api.domain.model.users.TemporaryAccount
import org.springframework.stereotype.Component

class TemporaryAccountService(
        private val repository : TemporaryAccountRepositoryInterFace
) {

    fun exists(temporaryAccount: TemporaryAccount) : Boolean {
        val result = repository.findByEmail(temporaryAccount.email)

        return result != null
    }
}