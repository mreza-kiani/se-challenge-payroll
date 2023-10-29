package com.waveapps.payroll.controllers.dto

data class PayrollReportResponse(val payrollReport: PayrollReport)

data class PayrollReport(var employeeReports: List<EmployeeReport>)

data class EmployeeReport(
    val employeeId: Int,
    val payPeriod: PayPeriod,
    val amountPaid: String
)

data class PayPeriod(
    val startDate: String,
    val endDate: String
)
