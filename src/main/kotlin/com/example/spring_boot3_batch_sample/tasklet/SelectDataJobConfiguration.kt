package com.example.spring_boot3_batch_sample.tasklet

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SelectDataJobConfiguration(
  private val selectDataTasklet: SelectDataTasklet,
  private val jobRepository: JobRepository,
  private val pratFormTransactionManager: PlatformTransactionManager
) {
  @Bean
  fun selectDataJob(selectDataStep: Step): Job {
    return JobBuilder("selectDataJob", jobRepository).start(selectDataStep).build()
  }
  @Bean
  fun selectDataStep(): Step {
    return StepBuilder("selectDataStep", jobRepository)
      .tasklet(selectDataTasklet, pratFormTransactionManager)
      .allowStartIfComplete(true) // 正常終了しても再実行可能にする設定
      .build()
  }
}
