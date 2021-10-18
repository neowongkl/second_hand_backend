package com.easygoing.backend.services.core.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DataFormatHelperTest{
    @Autowired
    private lateinit var dataFormatHelper: DataFormatHelper

    /**
     * Dummy data class
     */
    data class HongKonger (
        val age: Int,
        val name: String // standard getters setters
    )

    @Test
    fun objectToJsonTest(){
        val hongKonger = HongKonger(age = 25, name = "H K")
        val actualResult = dataFormatHelper.objectToJsonString(hongKonger)
        val expectedResult = "{\"age\":25,\"name\":\"H K\"}"
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun objectToJsonStringTest(){
        val hkJson = "{\"age\":17,\"name\":\"K H\"}"
        val result = dataFormatHelper.jsonToObject(hkJson, HongKonger::class.java)
        val expectedResult = HongKonger(age = 17, name = "K H")
        assertEquals(expectedResult, result)
    }

    @Test
    fun mapToJsonTest(){
        val map1 = mapOf(1 to "one", 2 to "two")
        val actualResult = dataFormatHelper.objectToJsonString(map1)
        val expectedResult = "{\"1\":\"one\",\"2\":\"two\"}"
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun jsonToMapTest(){
        val hkJson = "{\"age\":17,\"name\":\"K H\"}"
        val actualResult = dataFormatHelper.jsonToObject(hkJson, Map::class.java)
        val expectedResult = mapOf("age" to 17, "name" to "K H")
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun listToJson(){
        val list1 = listOf("one", "two", "three")
        val actualResult= dataFormatHelper.objectToJsonString(list1)
        val expectedResult = "[\"one\",\"two\",\"three\"]"
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun jsonToList(){
        val jsonString = "[\"one\",\"two\",\"three\"]"
        val actualResult = dataFormatHelper.jsonToObject(jsonString, List::class.java)
        val expectedResult = arrayListOf("one", "two", "three")
        assertEquals(expectedResult, actualResult)
    }

    // TODO jsonDateTimeFormatTest
}

