package com.waveapps.payroll.services

import com.waveapps.payroll.entities.JobGroup
import com.waveapps.payroll.repositories.JobGroupRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(private val jobGroupRepository: JobGroupRepository) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        seedJobGroups()
    }

    private fun seedJobGroups() {
        if (jobGroupRepository.count() == 0L) {
            val jobGroupA = JobGroup()
            jobGroupA.name = "A"
            jobGroupA.hourlyRate = 20.0

            val jobGroupB = JobGroup()
            jobGroupB.name = "B"
            jobGroupB.hourlyRate = 30.0

            jobGroupRepository.saveAll(listOf(jobGroupA, jobGroupB))
            logger.info("Created 2 records for job_groups table")
        }
    }
}