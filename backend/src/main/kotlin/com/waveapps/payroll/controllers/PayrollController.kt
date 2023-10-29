package com.waveapps.payroll.controllers

import com.waveapps.payroll.controllers.dto.PayrollReportResponse
import com.waveapps.payroll.services.CSVService
import com.waveapps.payroll.services.PayrollService
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin
@RestController
@RequestMapping("/api")
class PayrollController(
    private val payrollService: PayrollService,
    private val csvService: CSVService
) {

    @PostMapping("/upload")
    fun uploadCSV(@Validated @NotNull @RequestParam("file") file: MultipartFile) {
        csvService.processCSVFile(file)
    }

    @GetMapping("/report")
    fun getPayrollReport(): PayrollReportResponse {
        return PayrollReportResponse(payrollService.getPayrollReport())
    }
}