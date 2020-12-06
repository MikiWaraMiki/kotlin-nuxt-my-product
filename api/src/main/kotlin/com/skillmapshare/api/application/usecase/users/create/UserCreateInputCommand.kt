package com.skillmapshare.api.application.usecase.users.create

/**
 * ユーザ登録時の入力パラメータ
 */
data class UserCreateInputCommand(
  val uuid : String,
  val email : String,
  val userApplicationId : String,
  val displayName : String
)
