package com.waveapps.payroll.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidRequestException(msg: ErrorMessage) : RuntimeException(msg.name)