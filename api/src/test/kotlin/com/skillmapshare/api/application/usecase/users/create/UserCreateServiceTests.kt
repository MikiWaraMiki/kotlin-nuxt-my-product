package com.skillmapshare.api.application.usecase.users.create

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.skillmapshare.api.application.exception.ErrorStatusEnum
import com.skillmapshare.api.application.exception.UserOperationException
import com.skillmapshare.api.domain.infra.repository.users.UserRepositoryInterface
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.infra.mysql.model.users.UserOrm
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
/**
 * [com.skillmapshare.api.application.usecase.users.create.UserCreateService]のテスト
 */
class UserCreateServiceTests {
  private lateinit var userRepositoryMapper : UserRepositoryInterface
  private lateinit var userCreateService : UserCreateService
  private val inputCommand = UserCreateInputCommand(
    uuid = "a".repeat(20),
    email = "test_user@example.com",
    userApplicationId = "aaaaaa",
    displayName = "hogehoge"
  )
  private val userOrmMock = UserOrm(
    id = "a".repeat(20),
    email = "test_user@example.com",
    userApplicationId = "aaaaaa",
    userDisplayName = "hogehoge",
    createdDateTime = LocalDateTime.now(),
    updatedDateTime = LocalDateTime.now()
  )

  @Test
  @DisplayName("[正常系]登録に成功した場合は、UserCreateResultDotを返す")
  fun return_UserCreateResultDto_When_Success() {
    userRepositoryMapper = mock<UserRepositoryInterface> {
      on { findByEmail(any<Email>()) } doReturn null
      on { findByUserApplicationId(any()) } doReturn null
      on { save(any()) } doReturn userOrmMock
    }
    userCreateService = UserCreateService(
      userRepository = userRepositoryMapper
    )
    val result = userCreateService.createUser(inputCommand)

    Assertions.assertEquals(inputCommand.uuid, result.id)
    Assertions.assertEquals(inputCommand.email, result.email)
    Assertions.assertEquals(inputCommand.userApplicationId, result.userApplicationId)
    Assertions.assertEquals(inputCommand.displayName, result.displayName)
  }

  @Test
  @DisplayName("[異常系]すでにメールアドレスが登録済みの場合は、例外が発生すること")
  fun raise_UserOperationException_When_Email_Already_Exist() {
    userRepositoryMapper = mock<UserRepositoryInterface> {
      on { findByEmail(any()) } doReturn userOrmMock
    }
    userCreateService = UserCreateService(
      userRepository = userRepositoryMapper
    )
    val exception : UserOperationException = assertThrows<UserOperationException> {
      userCreateService.createUser(inputCommand)
    }

    Assertions.assertEquals("メールアドレスはすでに登録済みです", exception.msg)
    Assertions.assertEquals(
      ErrorStatusEnum.ALREADY_EXIST_DATA.getCode(),
      exception.getErrorStatusCode()
    )
  }

  @Test
  @DisplayName("[異常系]すでに登録されているアプリケーションIDを利用していた場合は、例外が発生すること")
  fun raise_UserOperationException_When_UserApplicationId_Already_Exist() {
    userRepositoryMapper = mock<UserRepositoryInterface> {
      on { findByEmail(any()) } doReturn null
      on { findByUserApplicationId(any()) } doReturn userOrmMock
    }
    userCreateService = UserCreateService(
      userRepositoryMapper
    )
    val exception = assertThrows<UserOperationException> {
      userCreateService.createUser(inputCommand)
    }

    Assertions.assertEquals(
      "ユーザーIDはすでに使用されているため、別のものを利用してください",
      exception.msg
    )
    Assertions.assertEquals(
      ErrorStatusEnum.ALREADY_EXIST_DATA.getCode(),
      exception.getErrorStatusCode()
    )
  }
}
