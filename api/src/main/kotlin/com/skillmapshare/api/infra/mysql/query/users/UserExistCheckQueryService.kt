package com.skillmapshare.api.infra.mysql.query.users

import org.springframework.stereotype.Repository

@Repository
/**
 * ユーザ作成時の、同値検査を行うQueryモデル
 * uuid, email, user_application_idが、一致しているか検査を行う
 */
class UserExistCheckQueryService {
}
