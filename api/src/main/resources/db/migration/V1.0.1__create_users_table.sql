CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) NOT NULL comment '認証Saasのuuid',
    email VARCHAR(255) NOT NULL comment 'ユーザのメールアドレス',
    user_application_id VARCHAR(100) NOT NULL comment 'ユーザがアプリ内で利用するid',
    display_name VARCHAR(255) NOT NULL comment 'ユーザの表示名',
    created_datetime DATETIME NOT NULL comment '登録日時',
    updated_datetime DATETIME NOT NULL comment '更新日時',

    PRIMARY KEY (id),
    UNIQUE (user_application_id),
    UNIQUE (email)
) COMMENT = 'ユーザテーブル';
