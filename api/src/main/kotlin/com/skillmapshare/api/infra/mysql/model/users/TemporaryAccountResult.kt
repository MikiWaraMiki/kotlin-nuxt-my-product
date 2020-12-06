package com.skillmapshare.api.infra.mysql.model.users

import java.time.LocalDateTime

data class TemporaryAccountResult (
        val id : Long,
        val email : String,
        val createdDateTime : LocalDateTime
)