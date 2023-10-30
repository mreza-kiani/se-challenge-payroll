package com.waveapps.payroll.services

import com.waveapps.payroll.controllers.dto.EmployeeReport
import com.waveapps.payroll.controllers.dto.PayrollReport
import com.waveapps.payroll.entities.TimeReport
import com.waveapps.payroll.repositories.TimeReportRepository
import com.waveapps.payroll.utilities.toCurrency
import com.waveapps.payroll.utilities.toPayPeriod
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


@Service
class PayrollService(private val timeReportRepository: TimeReportRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var payrollReport = PayrollReport(emptyList())

    fun getPayrollReport(): PayrollReport {
        return payrollReport
    }

    fun saveTimeReports(records: List<TimeReport>) {
        timeReportRepository.saveAll(records)
        updatePayrollReport()
    }

    @Scheduled(fixedDelay = 1000 * 60 * 2)
    fun updatePayrollReport() {
        payrollReport.employeeReports = timeReportRepository.findAll()
            .groupBy { it.employeeId }
            .flatMap { (employeeId, records) ->
                records
                    .groupBy { it.date.toPayPeriod() }
                    .map { (payPeriod, payPeriodRecords) ->
                        payPeriod to payPeriodRecords.sumOf { it.hoursWorked * it.jobGroup.hourlyRate }
                    }.map { (payPeriod, expectedPayAmount) ->
                        EmployeeReport(employeeId, payPeriod, amountPaid = expectedPayAmount.toCurrency())
                    }
            }.sortedBy { it.payPeriod.toString() }
            .sortedBy { it.employeeId }
        logger.info("Updated payroll report.")
    }

}