package com.skillmapshare.api.domain.model.users

/**
 * 認証Saas(Firebase)のUuidを表現するクラス
 */
class Uuid(_id : String) {
  var value : String = initializeUuid(_id)
    private set

  /**
   * uuidの初期化処理
   * @args _id : String
   * @return result : String
   */
  private fun initializeUuid(value : String) : String {
    if (isEmptyUuid(value)) {
      throw IllegalArgumentException("uuidが入力されていません")
    }
    if (isUsedUnSupportCharacter(value)) {
      throw IllegalArgumentException("uuidに使用できない文字が使われています")
    }
    return value
  }

  /**
   * ユーザのidの空文字判定
   */
  private fun isEmptyUuid(value : String) : Boolean {
    return value == ""
  }

  /**
   * ユーザidが英数字以外を利用しているか判定
   * 使用できる文字は英字数字のみ
   */
  private fun isUsedUnSupportCharacter(value : String) : Boolean {
    val regexPattern = "[a-zA-Z0-9]{1,255}".toRegex()

    return !regexPattern.matches(value)
  }
}
