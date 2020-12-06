CREATE TABLE IF NOT EXISTS user_icons (
    id INT(11) NOT NULL AUTO_INCREMENT comment 'ユーザアイコンid',
    user_id VARCHAR(255) NOT NULL comment 'ユーザid',
    file_path VARCHAR(255) NOT NULL comment 'ファイルの保存先のパス',

    PRIMARY KEY (id),
    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    UNIQUE (id, user_id)
) COMMENT = 'ユーザアイコンテーブル';
