package com.skillmapshare.api.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.gax.rpc.UnauthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.skillmapshare.api.application.exception.UserOperationException
import com.skillmapshare.api.auth.model.Credentials
import com.skillmapshare.api.auth.model.LoginUserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.lang.RuntimeException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

@Component
class FirebaseJwtAuthFilter : OncePerRequestFilter() {
  @Autowired
  private lateinit var objectMapper : ObjectMapper
  @Autowired
  private lateinit var securityService : UserSecurityAuthService

  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    auth(request)
    filterChain.doFilter(request, response)
  }

  private fun auth(request: HttpServletRequest) {
    val token = securityService.getBearerToken(request)
    if (token == "") {
      return
    }
    try {
      val decodedToken = securityService.decodeToken(token)
      if (!securityService.isExistUser(decodedToken)) {
        return
      }
      val userDetail = LoginUserDto(decodedToken)
      val authentication = UsernamePasswordAuthenticationToken(
        userDetail,
        Credentials("ID_TOKEN", decodedToken, token),
        null
      )
      SecurityContextHolder.getContext().authentication = authentication

    } catch(e : FirebaseAuthException) {
      logger.warn("Firebase認証の失敗が発生")
      logger.warn(e.message)
      throw RuntimeException(e)
    } catch (e : Exception) {
      logger.debug("システムエラー")
      logger.debug(e)
      throw RuntimeException(e)
    }
  }


}
