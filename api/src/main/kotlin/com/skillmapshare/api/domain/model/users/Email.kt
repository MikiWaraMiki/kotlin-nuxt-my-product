package com.skillmapshare.api.domain.model.users

class Email(_email: String) {
    var value: String
        private set

    init {
        if(_email == "") throw IllegalArgumentException("メールアドレスの入力は必須です")

        if( !this.validateFormat(_email) ) throw IllegalArgumentException("メールアドレスの形式が正しくありません")

        this.value = _email
    }

    private fun validateFormat(value: String) : Boolean {
        val emailPattern =  "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"
        val emailRegex = Regex(pattern = emailPattern)

        return emailRegex.matches(value)
    }
}