package com.skillmapshare.api.domain.model.users

/**
 * ユーザのアプリケーション利用時のidを表すクラス(ユーザIDとする)
 * UuidはFirebaseとなるため、別途アプリケーションで利用する個別のidを割り振っている
 */
class UserApplicationId(private val _value : String) {
  var value = initializeUserApplicationId(_value)
    private set

  /**
   * ユーザID
   * ルールに反していなければ、Stringを返す
   *
   * @args value 利用するユーザID
   * @return result String
   */
  private fun initializeUserApplicationId(value : String) : String {
    if (isEmptyString(value)) {
      throw IllegalArgumentException("ユーザIDの入力は必須です")
    }
    if (isOverCharCount(value)) {
      throw IllegalArgumentException("ユーザIDは30文字以内で入力してください")
    }
    if (isUsedUnSupportCharacter(value)) {
      throw IllegalArgumentException("ユーザIDには、英数字と_のみが利用できます")
    }
    return value
  }

  /**
   * アプリケーションidの空文字判定
   *
   * @args value 利用するユーザID
   * @return Boolean
   */
  private fun isEmptyString(value : String) : Boolean {
    return value == ""
  }
  /**
   * アプリケーションidの最大文字数判定
   * 最大30文字まで利用できる
   *
   * @args value 利用するユーザID
   * @return Boolean
   */
  private fun isOverCharCount(value : String) : Boolean {
    return value.length > 30
  }
  /**
   * 使用できる文字のみを利用しているか判定
   * 使用できる文字は英数字とアンスコのみ
   *
   * @args value 利用するユーザID
   * @return Boolean
   */
  private fun isUsedUnSupportCharacter(value : String) : Boolean {
    val regexPattern = "[a-zA-Z0-9_]{1,30}".toRegex()

    return !regexPattern.matches(value)
  }
}
