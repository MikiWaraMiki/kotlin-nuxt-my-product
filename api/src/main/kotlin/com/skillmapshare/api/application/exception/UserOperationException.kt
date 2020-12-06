package com.skillmapshare.api.application.exception

class UserOperationException(
        val msg : String?,
        private val errorStatus : ErrorStatusEnum
) : RuntimeException(msg) {
    fun getErrorStatusCode() : Number {
        return errorStatus.getCode()
    }
}