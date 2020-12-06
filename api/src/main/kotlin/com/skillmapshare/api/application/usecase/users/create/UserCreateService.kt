package com.skillmapshare.api.application.usecase.users.create

import com.skillmapshare.api.application.exception.ErrorStatusEnum
import com.skillmapshare.api.application.exception.UserOperationException
import com.skillmapshare.api.domain.infra.repository.users.UserRepositoryInterface
import com.skillmapshare.api.domain.model.users.*
import org.springframework.stereotype.Service

/**
 * 新規ユーザを作成するユースケースクラス
 */
@Service
class UserCreateService(
  private val userRepository : UserRepositoryInterface,
) {

  /**
   * 新規ユーザを作成する
   * @args command  UserCreateInputCommand コントローラが受け取ったリクエストパラメータ
   * @return result UserCreateResultDto コントローラが返すJSONレスポンス
   */
  fun createUser(command : UserCreateInputCommand) : UserCreateResultDto {
    val user = try {
      User(
        _uuid = Uuid(command.uuid),
        _email = Email(command.email),
        _userApplicationId = UserApplicationId(command.userApplicationId),
        _displayName = UserDisplayName(command.displayName),
        _userIcon = null
      )
    } catch (e : IllegalArgumentException) {
      throw UserOperationException(
        msg = e.message,
        errorStatus = ErrorStatusEnum.INPUT_PARAMETER_IS_INVALID
      )
    }
    // Todo(サービス層に写した方がいいかも？)
    val findByEmailResult = userRepository.findByEmail(user.email)
    if (findByEmailResult != null) {
      throw UserOperationException(
        msg = "メールアドレスはすでに登録済みです",
        errorStatus = ErrorStatusEnum.ALREADY_EXIST_DATA
      )
    }
    val findByApplicationIdResult = userRepository.findByUserApplicationId(
      user.userApplicationId
    )
    if (findByApplicationIdResult != null) {
      throw UserOperationException(
        msg = "ユーザーIDはすでに使用されているため、別のものを利用してください",
        errorStatus = ErrorStatusEnum.ALREADY_EXIST_DATA
      )
    }

    val result = userRepository.save(user)

    return UserCreateResultDto(
      id = result.id,
      email = result.email,
      userApplicationId = result.userApplicationId,
      displayName = result.userDisplayName
    )
  }
}
