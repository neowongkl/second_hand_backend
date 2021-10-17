package com.easygoing.backend.services.core.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataFormatHelper {
    @Autowired
    private lateinit var objectMapper : ObjectMapper

    // TODO("implement logger for exception handling")

    fun <T> jsonToObject(json: String, clazz: Class<T>): T?{
        return runCatching {
            objectMapper.readValue(json, clazz)
        }.onFailure { _exception->
            println(_exception)
            _exception.printStackTrace()
        }.getOrNull()
    }

    fun objectToJsonString(targetObject: Any): String?{
        return runCatching {
            objectMapper.writeValueAsString(targetObject)
        }.onFailure { _exception->
            println(_exception)
            _exception.printStackTrace()
        }.getOrNull()
    }

}
