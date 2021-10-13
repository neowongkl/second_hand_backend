package com.easygoing.backend.services.core.utilities

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataFormatHelper {
    @Autowired
    private lateinit var objectMapper : ObjectMapper

    //TODO("implement logger for exception handling")

    //TODO("josnToObject")

    fun objectToJsonString(targetObject: Any): String?{
        return runCatching {
            objectMapper.writeValueAsString(targetObject)
        }.onFailure { _exception->
            println(_exception)
            _exception.printStackTrace()
        }.getOrNull()
    }

}
