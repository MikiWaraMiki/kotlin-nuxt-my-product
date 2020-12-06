package com.skillmapshare.api.infra.db.mysql.users

import com.skillmapshare.api.domain.model.users.*
import com.skillmapshare.api.infra.mysql.model.users.UserOrm
import com.skillmapshare.api.infra.mysql.repository.users.UserRepository
import com.skillmapshare.api.util.annotation.JpaTestAnnotationMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@JpaTestAnnotationMapper
@Import(UserRepository::class)
class UserRepositoryTest {
  @Autowired
  private lateinit var testEntityManager: TestEntityManager
  @Autowired
  private lateinit var userRepository: UserRepository

  private lateinit var user : User
  @BeforeEach
  fun setup() {
    user = User(
      _uuid = Uuid("a".repeat(20)),
      _email = Email("sample@example.com"),
      _userApplicationId = UserApplicationId("sample"),
      _displayName = UserDisplayName("サンプル"),
      _userIcon = null
    )
    testEntityManager.persist(
      UserOrm(
        id = user.uuid.value,
        email = user.email.value,
        userApplicationId = user.userApplicationId.value,
        userDisplayName = user.displayName.name
      )
    )
  }

  @Nested
  @JpaTestAnnotationMapper
  @Import(UserRepository::class)
  @DisplayName("メールアドレスによる検索")
  inner class FindByEmailTest {
    @Test
    @DisplayName("存在する場合はUserOrmを返す")
    fun return_UserOrm_When_Exist() {
      val result = userRepository.findByEmail(user.email)
      Assertions.assertNotNull(result)
      if (result != null) {
        Assertions.assertEquals(user.email.value, result.email)
      }
    }
    @Test
    @DisplayName("存在しない場合はfalseを返す")
    fun return_UserOmr_When_Not_Exist() {
      val result = userRepository.findByEmail(Email("not_exist@example.com"))
      Assertions.assertNull(result)
    }
  }

  @Nested
  @JpaTestAnnotationMapper
  @Import(UserRepository::class)
  @DisplayName("アプリケーションidによる検索")
  inner class FindByUserApplicationIdTest {
    @Test
    @DisplayName("存在する場合はUserOrmを返す")
    fun return_UserOrm_When_Exist() {
      val result = userRepository.findByUserApplicationId(user.userApplicationId)
      Assertions.assertNotNull(result)

      if (result != null) {
        Assertions.assertEquals(user.userApplicationId.value, result.userApplicationId)
      }
    }

    @Test
    @DisplayName("存在しない場合はnullを返す")
    fun return_Null_When_Not_Exist() {
      val result = userRepository.findByUserApplicationId(
        UserApplicationId("not_exist")
      )
      Assertions.assertNull(result)
    }
  }

  @Nested
  @JpaTestAnnotationMapper
  @Import(UserRepository::class)
  @DisplayName("ユーザ作成のテスト")
  inner class SaveTest {
    @Test
    @DisplayName("レコードの登録が成功した場合は、UserOrmを返す")
    fun return_User_Orm_When_Crate_Success() {
      val user = User(
        _uuid = Uuid("b".repeat(10)),
        _email = Email("create@example.com"),
        _displayName = UserDisplayName("create_user"),
        _userApplicationId = UserApplicationId("b".repeat(20)),
        _userIcon = null
      )

      val result = userRepository.save(user)

      Assertions.assertEquals(user.uuid.value, result.id)
      Assertions.assertEquals(user.email.value, result.email)
      Assertions.assertEquals(user.userApplicationId.value, result.userApplicationId)
      Assertions.assertEquals(user.displayName.name, result.userDisplayName)
      Assertions.assertNotNull(result.createdDateTime)
      Assertions.assertNotNull(result.updatedDateTime)
    }
  }
}
