package com.skillmapshare.api.application.exception

import java.lang.RuntimeException

class InternalException(private val msg : String) : RuntimeException(msg) {
    fun sendSentry() {
        TODO("Sentryに通知")
    }
}