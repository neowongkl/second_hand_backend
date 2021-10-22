package com.easygoing.backend.services.core.util

import com.easygoing.backend.services.core.config.WebClientSyncConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class RestHelperImpl: RestHelper {

    //TODO add logger
    //TODO add 4xx & 5xx handler

    @Autowired
    private lateinit var webClientSyncConfiguration: WebClientSyncConfiguration

    private val webClientSync = WebClient.create()

    override fun <T> getForEntity(url: String, responseClass: Class<T>): T?{
        return runCatching {
            webClientSync.get()
                .uri(url)
                .attributes {  }
                .exchangeToMono { response->
                    if (response.statusCode().is2xxSuccessful){
                        return@exchangeToMono response.bodyToMono(responseClass)
                    }else{
                        return@exchangeToMono null
                    }
                }
                .block(webClientSyncConfiguration.timeout)
        }.onFailure { _exception->
            println(_exception)
            _exception.printStackTrace()
        }.getOrNull()
    }

    override fun <T> postForEntity(url: String, bodyObject: Any, responseClass: Class<T>): T? {
        return runCatching {
            webClientSync.post()
                .uri(url)
                .body(Mono.just(bodyObject), bodyObject.javaClass)
                .exchangeToMono{ response->
                    if (response.statusCode().is2xxSuccessful){
                        return@exchangeToMono response.bodyToMono(responseClass)
                    }else{
                        return@exchangeToMono null
                    }
                }
                .block(webClientSyncConfiguration.timeout)
        }.onFailure { _exception->
            println(_exception)
            _exception.printStackTrace()
        }.getOrNull()
    }

    override fun <T> delete(url: String, responseClass: Class<T>): T? {
        return runCatching {
            webClientSync.delete()
                .uri(url)
                .exchangeToMono { response->
                    if (response.statusCode().is2xxSuccessful){
                        return@exchangeToMono response.bodyToMono(responseClass)
                    }else{
                        return@exchangeToMono null
                    }
                }
                .block(webClientSyncConfiguration.timeout)
        }.onFailure { _exception->
            println(_exception)
            _exception.printStackTrace()
        }.getOrNull()
    }

    override fun delete(url: String): Boolean? {
        return runCatching {
            webClientSync.delete()
                .uri(url)
                .exchangeToMono { response->
                    if (response.statusCode().is2xxSuccessful){
                        return@exchangeToMono Mono.just(true)
                    }else{
                        return@exchangeToMono Mono.just(false)
                    }
                }
                .block(webClientSyncConfiguration.timeout)
        }.onFailure { _exception->
            println(_exception)
            _exception.printStackTrace()
        }.getOrNull()
    }

    override fun <T> patchForEntity(url: String, bodyObject: Any, responseClass: Class<T>) : T?{
        return runCatching {
            webClientSync.patch()
                .uri(url)
                .body(Mono.just(bodyObject), bodyObject.javaClass)
                .exchangeToMono{response->
                    if (response.statusCode().is2xxSuccessful){
                        return@exchangeToMono response.bodyToMono(responseClass)
                    }else{
                        return@exchangeToMono null
                    }
                }
                .block(webClientSyncConfiguration.timeout)
        }.onFailure { _exceptin->
            println(_exceptin)
            _exceptin.printStackTrace()
        }.getOrNull()
    }
}