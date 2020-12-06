package com.skillmapshare.api.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.skillmapshare.api.application.exception.InternalException
import com.skillmapshare.api.auth.model.LoginUserDto
import com.skillmapshare.api.domain.infra.repository.users.UserRepositoryInterface
import com.skillmapshare.api.domain.model.users.*
import com.skillmapshare.api.infra.mysql.model.users.UserOrm
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
/**
 * 認証ハンドラー
 */
class UserSecurityAuthService(
  private val securityProps : SecurityProperties,
  private val httpServletRequest: HttpServletRequest,
  private val userRepository : UserRepositoryInterface,
  private val firebaseAuth : FirebaseAuth
) {

  fun currentUser() : LoginUserDto? {
    val securityContext = SecurityContextHolder.getContext()
    val principal = securityContext.authentication.principal

    if ( principal is LoginUserDto) {
      return principal
    //  return generateUserFromLoginUserDto(principal)
    }
    return null
  }

  fun getBearerToken(request : HttpServletRequest) : String {
    val token = request.getHeader("Authorization")
    if (token == null || !token.startsWith("Bearer ")) {
      return ""
    }
    return token.substring("Bearer ".length)
  }

  fun decodeToken(token : String) : FirebaseToken {
    return firebaseAuth.verifyIdToken(token)
  }

  fun isExistUser(decodedToken : FirebaseToken) : Boolean {
    val result = findUserByUid(decodedToken.uid)
    return result != null
  }

  private fun generateUserFromLoginUserDto(loginUserDto: LoginUserDto): User {
    val userOrm = findUserByUid(loginUserDto.uid)
      ?: throw InternalException(
        msg = "LoginUserDtoからUserオブジェクトの変換でエラーが発生。uidのユーザが存在しなかった"
      )
    return User(
      _uuid = Uuid(userOrm.id),
      _email = Email(userOrm.email),
      _displayName = UserDisplayName(userOrm.userDisplayName),
      _userApplicationId = UserApplicationId(userOrm.userApplicationId),
      _userIcon = null
    )
  }

  private fun findUserByUid(uid : String) : UserOrm? {
    val uuid = Uuid(uid)
    return userRepository.findByUid(uuid)
  }
}
