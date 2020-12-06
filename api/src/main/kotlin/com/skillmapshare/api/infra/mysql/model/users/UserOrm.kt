package com.skillmapshare.api.infra.mysql.model.users

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserOrm (
  @Id
  @Column(name = "id", nullable = false, unique = true)
  val id: String,
  @Column(name = "email", nullable = false, unique = true)
  val email : String,
  @Column(name = "user_application_id", nullable = false, unique = true)
  val userApplicationId: String,
  @Column(name = "display_name", nullable = false)
  val userDisplayName: String,
  @Column(name = "created_datetime", nullable = false)
  val createdDateTime : LocalDateTime = LocalDateTime.now(),
  @Column(name = "updated_datetime", nullable = false)
  val updatedDateTime : LocalDateTime = LocalDateTime.now()
)
