package com.skillmapshare.api.controller.errors

import org.springframework.http.HttpStatus

enum class ResponseCodeEnum(val code : Number) {
    BAD_REQUEST(400) {
        override fun getHttpStatus() : HttpStatus {
            return HttpStatus.BAD_REQUEST
        }
    },
    UNPROCESSABLE_ENTITY(422) {
        override fun getHttpStatus(): HttpStatus {
            return HttpStatus.UNPROCESSABLE_ENTITY
        }
    },
    INTERNAL_SERVER_ERROR(500) {
        override fun getHttpStatus(): HttpStatus {
            return HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
    ;
    abstract fun getHttpStatus() : HttpStatus

    companion object {
        fun getHttpStatusByCode(code : Number) : HttpStatus {
            val enumVal = values().firstOrNull {
                it.code == code
            } ?: INTERNAL_SERVER_ERROR

            return enumVal.getHttpStatus()
        }
    }
}