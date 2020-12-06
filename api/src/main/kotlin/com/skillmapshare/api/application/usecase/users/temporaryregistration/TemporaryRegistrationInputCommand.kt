package com.skillmapshare.api.application.usecase.users.temporaryregistration

/**
 * 仮登録時の入力パラメータを表す
 * - email : 登録メールアドレス
 */
data class TemporaryRegistrationInputCommand (
  val email : String
)
