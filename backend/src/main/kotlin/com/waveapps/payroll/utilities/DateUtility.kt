package com.waveapps.payroll.utilities

import com.waveapps.payroll.controllers.dto.PayPeriod
import java.text.SimpleDateFormat
import java.util.*


fun Date.toPayPeriod(): PayPeriod {
    return if (this.getDayOfMonth() <= 15) {
        val startDate = this.setDayOfMonth(1)
        val endDate = this.setDayOfMonth(15)
        PayPeriod(startDate.toReportFormat(), endDate.toReportFormat())
    } else {
        val startDate = this.setDayOfMonth(16)
        val endDate = this.setDayOfMonth(this.getEndDayOfMonth())
        PayPeriod(startDate.toReportFormat(), endDate.toReportFormat())
    }
}

private fun Date.getEndDayOfMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun Date.getDayOfMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar[Calendar.DAY_OF_MONTH]
}

fun Date.setDayOfMonth(day: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar[Calendar.DAY_OF_MONTH] = day
    return calendar.time
}

fun Date.toReportFormat(): String {
    return SimpleDateFormat("yyyy-MM-dd").format(this)
}

fun String.toDate(): Date {
    return SimpleDateFormat("dd/MM/yyyy").parse(this)
}