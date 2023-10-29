package com.waveapps.payroll.services

import com.waveapps.payroll.entities.CSVFile
import com.waveapps.payroll.entities.TimeReport
import com.waveapps.payroll.exceptions.ErrorMessage
import com.waveapps.payroll.exceptions.InvalidRequestException
import com.waveapps.payroll.repositories.CSVFileRepository
import com.waveapps.payroll.repositories.JobGroupRepository
import com.waveapps.payroll.utilities.toDate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.sql.Timestamp

@Service
class CSVService(
    private val csvFileRepository: CSVFileRepository,
    private val jobGroupRepository: JobGroupRepository,
    private val payrollService: PayrollService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun processCSVFile(file: MultipartFile) {
        val csvId = extractCSVId(file.originalFilename)
        if (!isCSVIdValid(csvId))
            throw InvalidRequestException(ErrorMessage.CSV_ID_ALREADY_EXISTS)
        val csv = CSVFile().also { csvFile -> csvFile.id = csvId }
        val jobGroups = jobGroupRepository.findAll()

        try {
            val reader = BufferedReader(InputStreamReader(file.inputStream))
            reader.readLine()
            val timeReports = reader.useLines { lines ->
                lines.map { line ->
                    val columns = line.split(",")
                    val date = columns[0].toDate()
                    val hoursWorked = columns[1].toDouble()
                    val employeeId = columns[2].toInt()
                    val groupName = columns[3]
                    if (!jobGroups.any { it.name == groupName })
                        throw InvalidRequestException(ErrorMessage.INVALID_CSV_FILE)
                    val jobGroup = jobGroups.first { it.name == groupName }

                    TimeReport().also {
                        it.date = Timestamp(date.time)
                        it.employeeId = employeeId
                        it.hoursWorked = hoursWorked
                        it.csvFile = csv
                        it.jobGroup = jobGroup
                    }
                }.toList()
            }
            csvFileRepository.save(csv)
            payrollService.saveTimeReports(timeReports)
        } catch (e: Exception) {
            logger.error("Error happened while parsing the CSV!", e)
            throw InvalidRequestException(ErrorMessage.INVALID_CSV_FILE)
        }
        logger.info("Saved contents of #CSV-$csvId")
    }

    private fun isCSVIdValid(id: Int): Boolean {
        return csvFileRepository.findById(id).isEmpty
    }

    private fun extractCSVId(fileName: String?): Int {
        if (fileName.isNullOrEmpty())
            throw InvalidRequestException(ErrorMessage.INVALID_CSV_FILE)
        return fileName.split("-")[2].replace(".csv", "").toInt()
    }
}