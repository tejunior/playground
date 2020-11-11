package com.teo.playground

import com.teo.playground.services.ServiceConfig
import com.teo.playground.services.processRequest
import com.teo.playground.services.updateConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import reactor.kotlin.core.publisher.toMono

@Configuration
class Router {
    @Bean
    fun route() = router {
        POST("/any") { _ -> processRequest() }
        PUT("/service/config") { request -> updateConfig(request.bodyToMono(ServiceConfig::class.java)) }
    }
}
