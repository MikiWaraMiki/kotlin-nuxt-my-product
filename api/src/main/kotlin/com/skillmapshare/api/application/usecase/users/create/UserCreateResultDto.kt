package com.skillmapshare.api.application.usecase.users.create

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * ユーザ作成結果のDTO
 */
data class UserCreateResultDto(
  @JsonProperty("id")
  val id : String,
  @JsonProperty("email")
  val email : String,
  @JsonProperty("displayName")
  val displayName : String,
  @JsonProperty("userApplicationId")
  val userApplicationId: String
)
