package com.skillmapshare.api.config

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
/**
 * テスト実行時にDBのCleanUpとmigrateを行う
 */
class FlywayCleanerConfig {
  @Autowired
  private lateinit var flyway : Flyway
  @Bean
  // Todo マスタデータが入ってきたらテーブル除外が必要
  fun clean() = FlywayMigrationStrategy {
    flyway.clean()
    flyway.migrate()
  }
}
