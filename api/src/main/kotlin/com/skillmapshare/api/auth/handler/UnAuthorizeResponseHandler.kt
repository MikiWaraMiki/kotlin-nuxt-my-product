package com.skillmapshare.api.auth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UnAuthorizeResponseHandler(
  private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
  override fun commence(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    authException: AuthenticationException?
  ) {
    // APIベースのレスポンスに変更する
    val errorObject = mapOf<String, Any>(
      "message" to "Unauthorized Access",
      "error" to HttpStatus.UNAUTHORIZED,
      "code" to 401
    )
    if (response != null) {
      response.contentType = "application/json;charset=UTF-8"
      response.status = 401
      response.writer.write(objectMapper.writeValueAsString(errorObject))
    }
  }
}
