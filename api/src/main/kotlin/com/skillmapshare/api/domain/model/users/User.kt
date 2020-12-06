package com.skillmapshare.api.domain.model.users

/**
 * ユーザクラス
 */
class User(
  _uuid : Uuid,
  _email : Email,
  _userApplicationId: UserApplicationId,
  _displayName : UserDisplayName,
  _userIcon : UserIcon?
) {
  var uuid = _uuid
    private set
  var email = _email
    private set
  var userApplicationId = _userApplicationId
    private set
  var displayName = _displayName
    private set
  var userIcon = _userIcon
    private set

  /**
   * メールアドレスの変更
   */
  fun changeEmail(_email : Email) {
    this.email = _email
  }

  /**
   * 表示名の変更
   */
  fun changeDisplayName(_displayName: UserDisplayName) {
    this.displayName = _displayName
  }

  /**
   * アイコンの変更
   */
  fun changeIcon(_userIcon: UserIcon) {
    this.userIcon = _userIcon
  }
}
