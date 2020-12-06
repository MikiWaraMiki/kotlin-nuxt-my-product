package com.skillmapshare.api.controller.v1.users

import ch.qos.logback.core.status.ErrorStatus
import com.skillmapshare.api.application.exception.ErrorStatusEnum
import com.skillmapshare.api.application.exception.UserOperationException
import com.skillmapshare.api.application.usecase.users.create.UserCreateInputCommand
import com.skillmapshare.api.application.usecase.users.create.UserCreateResultDto
import com.skillmapshare.api.application.usecase.users.create.UserCreateService
import com.skillmapshare.api.auth.UserSecurityAuthService
import com.skillmapshare.api.domain.infra.repository.users.UserRepositoryInterface
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
/**
 * ユーザのCRUD処理を行うクラス
 * base_path: /api/v1/users
 */
class UserController(
  private val repositoryInterface: UserRepositoryInterface,
  private val usecase : UserCreateService,
  private val securityService : UserSecurityAuthService,
  private val request : HttpServletRequest
) {

  @PostMapping("/api/v1/users")
  @ResponseStatus(HttpStatus.CREATED)
    /**
     * ユーザの新規作成を行うコントローラ
     */
  fun create(
    @RequestBody inputCommand : UserCreateInputCommand
  ) : UserCreateResultDto {
    // Firebaseのトークン検証だけ行う
    val token  = securityService.getBearerToken(request)
    if (token == "") {
      throw UserOperationException("token is not set", ErrorStatusEnum.INVALID_TOKEN)
    }
    val decodedToken = securityService.decodeToken(token)
    if (decodedToken.uid == null) {
      throw UserOperationException("token is invalid", ErrorStatusEnum.INVALID_TOKEN)
    }
    val result = usecase.createUser(inputCommand)

    return result
  }
}
