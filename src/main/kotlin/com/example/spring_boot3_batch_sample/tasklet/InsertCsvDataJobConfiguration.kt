package com.example.spring_boot3_batch_sample.tasklet

import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.Job
import org.springframework.context.annotation.Bean
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class InsertCsvDataJobConfiguration(
  private val insertCsvDataTasklet: InsertCsvDataTasklet,
  private val jobRepository: JobRepository,
  private val pratFormTransactionManager: PlatformTransactionManager
) {

    @Bean
    fun insertCsvDataJob(insertCsvDataStep: Step): Job {
      return JobBuilder("insertCsvDataJob", jobRepository).start(insertCsvDataStep).build()
    }

    @Bean
    fun insertCsvDataStep(): Step {
      return StepBuilder("insertCsvDataStep", jobRepository)
        .tasklet(insertCsvDataTasklet, pratFormTransactionManager)
        .allowStartIfComplete(true) // 正常終了しても再実行可能にする設定
        .build()
    }
}
