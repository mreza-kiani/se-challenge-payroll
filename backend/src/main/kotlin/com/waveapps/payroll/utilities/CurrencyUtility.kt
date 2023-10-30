package com.waveapps.payroll.utilities

fun Double.toCurrency(): String {
    val formattedAmount = "%.2f".format(this)
    return "$$formattedAmount"
}