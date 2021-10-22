package com.easygoing.backend.services.core.util

interface RestHelper {
    fun <T> getForEntity(url: String, responseClass: Class<T>): T?
    fun <T> postForEntity(url: String, bodyObject: Any, responseClass: Class<T>): T?
    fun <T> delete(url: String, responseClass: Class<T>): T?
    fun <T> patchForEntity(url: String, bodyObject: Any, responseClass: Class<T>) : T?
}
