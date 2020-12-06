package com.skillmapshare.api.controller.errors

import com.skillmapshare.api.application.exception.UserOperationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@ControllerAdvice(
        annotations = [RestController::class]
)
@Component
class ErrorResponseHandler {
    @ExceptionHandler(UserOperationException::class)
    fun handleUserOperationException(e:UserOperationException) : ResponseEntity<UserErrorResponse> {
        val httpResponseCode = ResponseCodeEnum.getHttpStatusByCode(e.getErrorStatusCode())
        val errorResponseBody = UserErrorResponse(
                status = e.getErrorStatusCode(),
                error = e.message,
                stacktrace = null
        )
        return ResponseEntity(
                errorResponseBody,
                httpResponseCode
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleInternalServerError(e : Exception) : InternalErrorResponse {
        sendSentry()

        return InternalErrorResponse(
                status = 500,
                error = e.message,
                traceback = e.stackTrace.toString()
        )
    }

    private fun sendSentry() {
        TODO("Sentry通知処理")
    }
}