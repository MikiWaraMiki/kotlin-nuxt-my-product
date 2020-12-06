CREATE TABLE IF NOT EXISTS temporary_accounts (
    id INT(11) NOT NULL AUTO_INCREMENT comment '仮登録id',
    email VARCHAR(255) NOT NULL comment '仮登録時のメールアドレス',
    email_verification_token VARCHAR(255) NOT NULL comment 'メール検証用のトークン',
    token_expired_datetime DATETIME NOT NULL comment '検証用トークンの失効日時',
    created_datetime DATETIME NOT NULL comment '登録日時',
    updated_datetime DATETIME NOT NULL comment '更新日時',

    PRIMARY KEY (id)
) COMMENT = 'ユーザー仮登録テーブル';

-- UNIQE INDEX
CREATE UNIQUE INDEX uq_temporary_accounts_by_email ON temporary_accounts(email);
