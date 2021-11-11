package com.easygoing.backend.services.core.excpetion

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class BadRequestException: RuntimeException {
    constructor(message: String) : super(message)
}