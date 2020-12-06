package com.skillmapshare.api.infra.mysql.repository.users;

import com.skillmapshare.api.domain.infra.repository.users.UserRepositoryInterface
import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.User
import com.skillmapshare.api.domain.model.users.UserApplicationId
import com.skillmapshare.api.domain.model.users.Uuid
import com.skillmapshare.api.infra.mysql.model.users.UserOrm
import org.springframework.stereotype.Repository;

@Repository
class UserRepository(
  private val jpaRepository: JpaUserRepository
) : UserRepositoryInterface {

  /**
   * firebaseが発行したuidを元に検索
   */
  override fun findByUid(uid: Uuid): UserOrm? {
    val result = jpaRepository.findById(uid.value)

    return result.orElse(null)
  }
  /**
   * Emailを元に検索
   */
  override fun findByEmail(email: Email): UserOrm? {
    return jpaRepository.findByEmail(email.value)
  }

  /**
   * ユーザが利用するアプリidによる検索
   */
  override fun findByUserApplicationId(userApplicationId: UserApplicationId): UserOrm? {
    return jpaRepository.findByUserApplicationId(userApplicationId.value)
  }

  /**
   * ユーザの作成
   * @args user User 作成するUserモデル
   * @return UserOrm 作成されたUserOrmモデル
   */
  override fun save(user: User): UserOrm {
    val userOrm = UserOrm(
      id = user.uuid.value,
      email = user.email.value,
      userApplicationId = user.userApplicationId.value,
      userDisplayName = user.displayName.name
    )
    return jpaRepository.save(userOrm)
  }
}
