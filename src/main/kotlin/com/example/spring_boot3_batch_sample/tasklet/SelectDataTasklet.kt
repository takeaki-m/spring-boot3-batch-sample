package com.example.spring_boot3_batch_sample.tasklet

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
@StepScope
class SelectDataTasklet(
    private val jdbcTemplate: JdbcTemplate
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        try {

            jdbcTemplate.query("SELECT * FROM PEOPLE") { rs, _ ->
                println("ID: ${rs.getInt("person_id")}")
                println("Name: ${rs.getString("first_name")}")
                println("Age: ${rs.getString("last_name")}")
                println("================================")
            }
        } catch (e: Exception) {
            e.stackTrace
            return RepeatStatus.FINISHED
        }

        println("================================")
        println("Select Data Tasklet")
        println("================================")
        return RepeatStatus.FINISHED
    }
}
