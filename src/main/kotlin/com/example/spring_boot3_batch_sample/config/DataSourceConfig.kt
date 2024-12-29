package com.example.spring_boot3_batch_sample.config

import javax.sql.DataSource
import org.springframework.boot.autoconfigure.batch.BatchDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * DataSource設定クラス
 *
 * Spring Batch が利用するdatasourceを使い分けられるようにする Spring Batchでは、以下二つのdatasourceが使われる。
 * - Spring Batchのmetadataを管理するDB
 * - 業務で利用するDB
 */
@Configuration
class DataSourceConfig {

  /**
   * Spring Batchで利用するBatch Meta Dataを管理するDBの設定
   *
   * in-memoryのh2-databaseを利用 prefixに対応する、application.ymlで定義の設定値が適用される
   *
   * @return Spring Batch実行管理用datasource設定
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.h2metadata")
  fun batchManageDataSourceProperties(): DataSourceProperties {
    return DataSourceProperties()
  }

  /**
   * 業務DBの設定
   *
   * prefixに対応する、application.ymlで定義の設定値が適用される
   *
   * @return postgresql DBのdatasource設定
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.business")
  fun postgresqlDatasourceProperties(): DataSourceProperties {
    return DataSourceProperties()
  }

  /**
   * Spring Batchで利用するBatch Meta DataのDataSource設定
   *
   * in-memoryのh2databaseを利用 設定値を元にdatasourceを作成する [BatchDataSource] annotationは、Batch Meta
   * Dataの管理に利用することを示す
   *
   * @return Spring Batch実行管理用datasource
   */
  @Bean
  @BatchDataSource
  fun batchManageDataSource(): DataSource {
    return batchManageDataSourceProperties().initializeDataSourceBuilder().build()
  }

  /**
   * 業務DBの設定
   *
   * 設定値を元にdatasourceを作成する [Primary] annotationはアプリケーションで利用するmainのdatasourceであることを示す
   * [Primary]が付与されたdatasourceに対して、transaction managerは自動的に生成される
   *
   * @return 業務DB postgresql のdatasource
   */
  @Bean
  @Primary
  fun postgresqlDatasource(): DataSource {
    return postgresqlDatasourceProperties().initializeDataSourceBuilder().build()
  }
}
