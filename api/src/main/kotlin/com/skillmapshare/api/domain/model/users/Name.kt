package com.skillmapshare.api.domain.model.users

/**
 * 利用者の名前クラス
 */
class Name(_firstName : String, _lastName : String) {
  var firstName : String
    private set
  var lastName : String
    private set

  init {
    if (isEmptyValue(_firstName)) {
      throw IllegalArgumentException("名前が入力されていません")
    }
    if (isEmptyValue(_lastName)) {
      throw IllegalArgumentException("苗字が入力されていません")
    }

    firstName = _firstName
    lastName = _lastName
  }

  /**
   * フルネームを返す
   */
  fun getFullName() : String {
    return lastName + firstName
  }

  /**
   * 空文字判定
   */
  private fun isEmptyValue(value : String) : Boolean {
    return value == ""
  }
}
