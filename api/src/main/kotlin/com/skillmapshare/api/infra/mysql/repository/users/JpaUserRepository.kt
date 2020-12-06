package com.skillmapshare.api.infra.mysql.repository.users

import com.skillmapshare.api.infra.mysql.model.users.UserOrm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaUserRepository : JpaRepository<UserOrm, String> {
  fun findByEmail(email : String) : UserOrm?
  fun findByUserApplicationId(userApplicationId : String) : UserOrm?
}
