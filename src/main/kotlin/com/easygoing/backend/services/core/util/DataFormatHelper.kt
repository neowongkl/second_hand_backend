package com.easygoing.backend.services.core.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import kotlin.math.log

@Component
class DataFormatHelper {
    @Autowired
    private lateinit var objectMapper : ObjectMapper

    private val logger = LoggerFactory.getLogger(DataFormatHelper::class.java)

    fun <T> jsonFileToObject(src: File, clazz: Class<T>): T?{
        return runCatching {
            objectMapper.readValue(src, clazz)
        }.onFailure { _exception->
            logger.error("Fail to covert json file to object")
            logger.error("json file: ${src.name}")
            logger.error("class: $clazz")
            logger.error(_exception.toString())
            _exception.printStackTrace()
        }.getOrNull()
    }

    fun <T> jsonToObject(json: String, clazz: Class<T>): T?{
        return runCatching {
            objectMapper.readValue(json, clazz)
        }.onFailure { _exception->
            logger.error("Fail to covert json to object")
            logger.error("json: $json")
            logger.error("class: $clazz")
            logger.error(_exception.toString())
            _exception.printStackTrace()
        }.getOrNull()
    }

    fun objectToJsonString(targetObject: Any): String?{
        return runCatching {
            objectMapper.writeValueAsString(targetObject)
        }.onFailure { _exception->
            logger.error("fail to convert object to string")
            logger.error("object: $targetObject")
            logger.error(_exception.toString())
            _exception.printStackTrace()
        }.getOrNull()
    }

}
