package com.easygoing.backend.services.core.utilities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["LOCAL"])
class DataFormatHelperTest{
    @Autowired
    private lateinit var dataFormatHelper: DataFormatHelper

    @Test
    fun objectToJsonTest(){
        val hongKonger = HongKonger(age = 25, name = "H K")
        val result = dataFormatHelper.objectToJsonString(hongKonger)
        val expectedResult = "{\"age\":25,\"name\":\"H K\"}"
        assertEquals(result, expectedResult)
    }

    @Test
    fun objectToJsonStringTest(){
        val hkJson = "{\"age\":17,\"name\":\"K H\"}"
        val result = dataFormatHelper.jsonToObject(hkJson, HongKonger::class.java)
        val expectedResult = HongKonger(age = 17, name = "K H")
        assertEquals(result, expectedResult)
    }
}

data class HongKonger (
    val age: Int,
    val name: String // standard getters setters
)