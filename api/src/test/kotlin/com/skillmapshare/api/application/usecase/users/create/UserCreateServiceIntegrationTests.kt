package com.skillmapshare.api.application.usecase.users.create

import com.skillmapshare.api.application.exception.UserOperationException
import com.skillmapshare.api.config.FlywayCleanerConfig
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.infra.mysql.repository.users.UserRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import javax.persistence.EntityManager

@SpringBootTest
@Import(FlywayCleanerConfig::class)
/**
 * [com.skillmapshare.api.application.usecase.users.create.UserCreateService]の統合テスト
 */
class UserCreateServiceIntegrationTests {
  @Autowired
  private lateinit var entityManager : EntityManager
  @Autowired
  private lateinit var userRepository: UserRepository
  @Autowired
  private lateinit var userCreateService : UserCreateService

  @Test
  @DisplayName("[正常系] 新規ユーザの登録ができる場合は、UserCreateResultDtoを返していること")
  fun return_UserCreateResult_Dto_When_Success() {
    val inputCommand = inputCommandBuilder("1")
    val result = userCreateService.createUser(inputCommand)

    Assertions.assertEquals(result.id, inputCommand.uuid)
  }

  @Test
  @DisplayName("[正常系]新規ユーザの登録した場合は、DBにレコードが登録されていること")
  fun record_Created_When_Success() {
    val inputCommand = inputCommandBuilder("2")
    userCreateService.createUser(inputCommand)

    val findByResult = userRepository.findByEmail(Email(inputCommand.email))
    Assertions.assertNotNull(findByResult)
  }

  @Test
  @DisplayName("[異常系]メールアドレスが登録されている場合はエラーが発生すること")
  fun raise_Error_When_AlreadyExistEmail() {
    // 事前に登録
    val inputCommand = inputCommandBuilder("3")
    userCreateService.createUser(inputCommand)

    val exception = assertThrows<UserOperationException> {
      userCreateService.createUser(inputCommand)
    }
    Assertions.assertEquals("メールアドレスはすでに登録済みです", exception.message)
  }

  @Test
  @DisplayName("[異常系]アプリケーションidが登録されている場合はエラーが発生すること")
  fun raise_Error_When_AlreadyExistApplicationId() {
    // 事前に登録
    val inputCommand = inputCommandBuilder("4")
    userCreateService.createUser(inputCommand)
    val expectRaiseErrorCommand = inputCommandBuilder(
      prefix = "5",
      userApplicationId = inputCommand.userApplicationId
    )
    val exception = assertThrows<UserOperationException> {
      userCreateService.createUser(expectRaiseErrorCommand)
    }

    Assertions.assertEquals(
      "ユーザーIDはすでに使用されているため、別のものを利用してください",
      exception.message
    )
  }

  @Test
  @DisplayName("[異常系]必要項目が入力されていない場合はエラーが発生すること")
  fun raise_Error_When_Empty_Required_Value() {
    val inputCommand = inputCommandBuilder(
      "5",
      email = ""
    )
    val exception = assertThrows<UserOperationException> {
      userCreateService.createUser(inputCommand)
    }

    Assertions.assertEquals(
      "メールアドレスの入力は必須です",
      exception.message
    )
  }

  private fun inputCommandBuilder(
    prefix : String,
    uuid : String? = null,
    email : String? = null,
    displayName: String? = null,
    userApplicationId: String? = null
  ) : UserCreateInputCommand {
    return UserCreateInputCommand(
      uuid = uuid ?: "a".repeat(20) + prefix,
      email = email ?: "create_user_$prefix@examle.com",
      displayName = displayName ?: "create_user_$prefix",
      userApplicationId = userApplicationId ?: "create_user_$prefix"
    )
  }
}
