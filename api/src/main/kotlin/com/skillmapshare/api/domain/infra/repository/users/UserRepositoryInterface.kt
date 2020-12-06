package com.skillmapshare.api.domain.infra.repository.users

import com.skillmapshare.api.domain.model.users.Email
import com.skillmapshare.api.domain.model.users.User
import com.skillmapshare.api.domain.model.users.UserApplicationId
import com.skillmapshare.api.domain.model.users.Uuid
import com.skillmapshare.api.infra.mysql.model.users.UserOrm

interface UserRepositoryInterface {
  fun findByUid(uid : Uuid) : UserOrm?
  fun findByEmail(email : Email) : UserOrm?
  fun findByUserApplicationId(userApplicationId: UserApplicationId) : UserOrm?
  fun save(user : User) : UserOrm
}
