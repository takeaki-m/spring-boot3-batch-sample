package com.example.spring_boot3_batch_sample.tasklet

import com.example.spring_boot3_batch_sample.domain.People
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
@StepScope
class InsertCsvDataTasklet(private val jdbcTemplate: JdbcTemplate) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val inputStream = ClassPathResource("sample-data.csv").getInputStream()
        val targetUsers = readCsv(inputStream)
        for (user in targetUsers) {

            jdbcTemplate.update("INSERT INTO PEOPLE (first_name, last_name) VALUES (?, ?)", user.firstName, user.lastName)
        }
        println("================================")
        println("Insert Data Tasklet")
        println("================================")
        return RepeatStatus.FINISHED
    }

    fun readCsv(inputStream: InputStream) : ArrayList<People> {
        val reader = inputStream.bufferedReader()
        return reader.lineSequence().filter { it.isNotBlank() }
            .map { val (firstName, lastName) =
                it.split(',', ignoreCase = false, limit = 2)
                People(firstName, lastName)
            }.toList() as ArrayList<People>
    }
}
