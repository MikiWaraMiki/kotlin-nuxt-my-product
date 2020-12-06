/**
 * validationのプラグインクラス
 */
export class Validator {
  // eslint-disable-next-line no-useless-constructor
  constructor() {}

  require(
    value: Number | String | null,
    message: String = '入力してください'
  ): String | Boolean {
    if (value === null || value === '') return message
    return true
  }

  minLength(
    value: string = '',
    min: number,
    message: string = ''
  ): String | Boolean {
    const errorMessage =
      message || `${min.toString()}文字以上で入力してください`
    if (value === null || value === '') return true

    return value.length > min || errorMessage
  }

  maxLength(
    value: string = '',
    max: number,
    message: string = ''
  ): string | boolean {
    const errorMessage =
      message || `${max.toString()}文字以内で入力してください`
    if (value === null || value === '') return true

    return value.length < max || errorMessage
  }

  email(value: string | null): string | boolean {
    const emailRegexPattern = /^[A-Za-z0-9]{1}[A-Za-z0-9_.&%+\-']*@{1}[A-Za-z0-9_.-]{1,}\.[A-Za-z0-9]{1,}$/
    if (!value) return true

    return (
      emailRegexPattern.test(value) || 'メールアドレスの形式が正しくありません'
    )
  }
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default ({ app }: any, inject: any) => {
  inject('validator', new Validator())
}
