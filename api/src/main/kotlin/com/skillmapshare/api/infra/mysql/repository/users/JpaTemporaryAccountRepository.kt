package com.skillmapshare.api.infra.mysql.repository.users

import com.skillmapshare.api.infra.mysql.model.users.TemporaryAccountOrm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaTemporaryAccountRepository : JpaRepository<TemporaryAccountOrm, Long> {
    fun findByEmail(email : String): TemporaryAccountOrm?
}