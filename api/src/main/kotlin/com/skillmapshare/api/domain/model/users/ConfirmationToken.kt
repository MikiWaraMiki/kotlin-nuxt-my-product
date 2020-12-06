package com.skillmapshare.api.domain.model.users

import org.springframework.security.crypto.bcrypt.BCrypt

class ConfirmationToken {
    var token : String = generateToken()
        private set

    private fun generateToken() : String {
        val salt  = BCrypt.gensalt()
        return BCrypt.hashpw(getStrUnixTime(), salt)
    }

    private fun getStrUnixTime() : String {
        val unixTime = System.currentTimeMillis()
        return unixTime.toString()
    }
}