package com.skillmapshare.api.auth.model

import com.google.firebase.auth.FirebaseToken

data class Credentials(
  var type : String,
  var decodedToken : FirebaseToken,
  var idToken : String
)
