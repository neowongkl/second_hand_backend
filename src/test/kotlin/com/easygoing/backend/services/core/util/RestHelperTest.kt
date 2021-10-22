package com.easygoing.backend.services.core.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate

@SpringBootTest
class RestHelperTest {

    @Autowired
    private lateinit var restHelper: RestHelper

    //dummy data class
    data class Post(
        var userId: Int,
        var id: Int? = null,
        var title: String,
        var body: String
    )

    @Test
    fun doGetEntityTest(){
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val entity = restHelper.getForEntity(url, Post::class.java)
        val expectedResult = 1
        Assertions.assertNotNull(entity)
        Assertions.assertEquals(1, entity!!.id)
    }

    @Test
    fun doGetEntityListTest(){
        val url = "https://jsonplaceholder.typicode.com/posts"
        val entity = restHelper.getForEntity(url, Array<Post>::class.java)
        val expectResult = 100
        Assertions.assertNotNull(entity)
        Assertions.assertEquals(100, entity!!.size)
    }

    @Test
    fun doPostEntityTest(){
        val url = "https://jsonplaceholder.typicode.com/posts"
        val body = Post(
            title = "foo",
            body = "bar",
            userId = 1
        )
        val entity = restHelper.postForEntity(url, body, Post::class.java)
        val expectedResult = Post(
            id = 101,
            title = "foo",
            body = "bar",
            userId = 1
        )
        Assertions.assertEquals(expectedResult, entity)
    }

    @Test
    fun doDeleteEntityTest(){
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val entity = restHelper.delete(url, String::class.java)
        val expectedResult = "{}"
        Assertions.assertEquals(expectedResult, entity)
    }

    @Test
    fun doDeleteBooleanTest(){
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val entity = restHelper.delete(url)
        Assertions.assertNotNull(entity)
        Assertions.assertTrue(entity!!)
    }

    @Test
    fun doPatchEntityTest(){
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val body = Post(
            userId = 1,
            id = 1,
            title = "sff",
            body = "2222"
        )
        val entity = restHelper.patchForEntity(url, body, Post::class.java)
        Assertions.assertEquals(body, entity)
    }



}