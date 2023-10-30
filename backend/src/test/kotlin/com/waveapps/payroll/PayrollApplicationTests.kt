package com.waveapps.payroll

import com.waveapps.payroll.exceptions.ErrorMessage
import com.waveapps.payroll.exceptions.InvalidRequestException
import com.waveapps.payroll.repositories.CSVFileRepository
import com.waveapps.payroll.repositories.TimeReportRepository
import com.waveapps.payroll.services.CSVService
import com.waveapps.payroll.services.PayrollService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.multipart.MultipartFile


@SpringBootTest
@ActiveProfiles(value = ["test"])
class PayrollApplicationTests {

    @Autowired
    lateinit var csvService: CSVService

    @Autowired
    lateinit var payrollService: PayrollService

    @Autowired
    lateinit var csvFileRepository: CSVFileRepository

    @Autowired
    lateinit var timeReportRepository: TimeReportRepository

    private lateinit var multipartFile: MultipartFile

    @BeforeEach
    fun importCSV() {
        val resource = javaClass.classLoader.getResource("static/time-report-42.csv")
        multipartFile = MockMultipartFile("mock-file", "time-report-42.csv", "text/csv", resource.openStream())
        csvService.processCSVFile(multipartFile)
    }

    @AfterEach
    fun cleanUp() {
        timeReportRepository.deleteAll()
        csvFileRepository.deleteAll()
    }

    @Test
    fun testSuccessfulCSVImport() {
        val csvFiles = csvFileRepository.findAll()
        Assertions.assertEquals(1, csvFiles.count())
        Assertions.assertEquals(42, csvFiles.first().id)

        val timeReports = timeReportRepository.findAll()
        Assertions.assertEquals(31, timeReports.count())
    }

    @Test
    fun testImportingACsvTwice() {
        val exception = Assertions.assertThrows(InvalidRequestException::class.java) {
            csvService.processCSVFile(multipartFile)
        }
        Assertions.assertEquals(ErrorMessage.CSV_ID_ALREADY_EXISTS.name, exception.message)
    }

    @Test
    fun testReportingResults() {
        val payrollReport = payrollService.getPayrollReport()
        Assertions.assertEquals(13, payrollReport.employeeReports.size)
        Assertions.assertEquals("$150.00", payrollReport.employeeReports.first().amountPaid)
        Assertions.assertEquals("2023-11-01", payrollReport.employeeReports.first().payPeriod.startDate)
        Assertions.assertEquals("2023-11-15", payrollReport.employeeReports.first().payPeriod.endDate)
        Assertions.assertEquals("$220.00", payrollReport.employeeReports[1].amountPaid)
        Assertions.assertEquals("2023-11-16", payrollReport.employeeReports[1].payPeriod.startDate)
        Assertions.assertEquals("2023-11-30", payrollReport.employeeReports[1].payPeriod.endDate)
        val employee4Record = payrollReport.employeeReports.first { it.employeeId == 4 }
        Assertions.assertEquals("$150.00", employee4Record.amountPaid)
        Assertions.assertEquals("2023-02-16", employee4Record.payPeriod.startDate)
        Assertions.assertEquals("2023-02-28", employee4Record.payPeriod.endDate)
    }

    @Test
    fun testReportOrder() {
        val records = payrollService.getPayrollReport().employeeReports
        Assertions.assertEquals(true, records.mapIndexed { index, record ->
            if (index == 0) true
            else (record.payPeriod.toString() > records[index - 1].payPeriod.toString())
                    || (record.employeeId > records[index - 1].employeeId)

        }.all { it })
    }

}
