package com.skillmapshare.api.application.exception

/**
 * ユーザー起因で発生したエラーコードを定義するEnum
 */
enum class ErrorStatusEnum {
  INPUT_PARAMETER_IS_REQUIRED {
    override fun getCode(): Number {
      return 400
    }
  },
  INPUT_PARAMETER_IS_INVALID {
    override fun getCode(): Number {
      return 422
    }
  },
  ALREADY_EXIST_DATA {
    override fun getCode(): Number {
      return 400
    }
  },
  UNSET_TOKEN {
    override fun getCode(): Number {
      return 401
    }
  },
  INVALID_TOKEN {
    override fun getCode(): Number {
      return 401
    }
  };


  abstract fun getCode(): Number
}
