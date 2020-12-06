package com.skillmapshare.api.application.usecase.users.temporaryregistration

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 仮登録完了時にユーザに返すパラメータを保持するクラス
 */
data class TemporaryRegistrationResultDto (
  @JsonProperty("id")
  val id : Long,
  @JsonProperty("email")
  val email : String
)
