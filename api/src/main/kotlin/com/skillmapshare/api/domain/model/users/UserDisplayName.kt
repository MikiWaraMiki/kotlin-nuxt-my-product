package com.skillmapshare.api.domain.model.users

/**
 * サービス内で表示する名前を表すクラス
 */
class UserDisplayName(_name : String) {
  var name = initializeUserDisplayName(_name)
    private set

  /**
   * サービス内で表示する名前を初期化
   * @args value 利用する名前
   * @return result String
   */
  private fun initializeUserDisplayName(value: String) : String {
    if (isEmptyString(value)) {
      throw IllegalArgumentException("表示名が入力されていません")
    }
    if (isOverCharCount(value)) {
      throw IllegalArgumentException("表示名は50文字以内で入力してください")
    }
    return value
  }

  /**
   * 空文字判定を行う
   */
  private fun isEmptyString(value: String) : Boolean {
    return value == ""
  }
  /**
   * 利用している文字数判定
   * 最大50文字まで利用できる
   * @args value 利用する名前
   * @return Boolean
   */
  private fun isOverCharCount(value : String) : Boolean {
    return value.length > 50
  }
}
