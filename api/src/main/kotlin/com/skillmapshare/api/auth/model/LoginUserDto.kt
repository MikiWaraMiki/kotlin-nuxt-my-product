package com.skillmapshare.api.auth.model

import com.google.firebase.auth.FirebaseToken
import com.skillmapshare.api.domain.model.users.User

/**
 * ログインしたユーザを示すクラス
 */
class LoginUserDto(token: FirebaseToken) {
  var uid = token.uid
    private set
  var name = token.name
    private set
  var email = token.email
    private set
  var picture = token.picture
    private set
  var issuer = token.issuer
    private set
  var isEmailVerified = token.isEmailVerified
}
