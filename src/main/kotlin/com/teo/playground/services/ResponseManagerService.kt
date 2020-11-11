package com.teo.playground.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Thread.sleep
import kotlin.random.Random

data class ResponseConfig(val fromPercent: Int, val toPercent: Int, val httpCode: Int)

data class DelayConfig(
    val fromPercent: Int,
    val toPercent: Int,
    val minDelay: Long,
    val maxDelay: Long)

data class ServiceConfig(val responses: List<ResponseConfig>, val delays: List<DelayConfig>)

val logger: Logger = LoggerFactory.getLogger("ResponseManagerService")

@Volatile
var serviceConfig = ServiceConfig(
    listOf(ResponseConfig(1, 100, 200)),
    listOf(DelayConfig(1, 100, 200, 300))
)

// Execute in a new Thread in order to not block the event loop
fun processRequest(): Mono<ServerResponse> {
    return Mono.fromCallable { delaySimulate() }
        .subscribeOn(Schedulers.elastic())
        .flatMap { response().build() };
}

fun updateConfig(serverConfigMono: Mono<ServiceConfig>): Mono<ServerResponse> {
    return serverConfigMono
        .doOnSuccess {
            logger.info("Updating configuration {}", it)
            serviceConfig = it
        }
        .flatMap { ok().build() }
}

private fun response(): BodyBuilder {
    val percent = Random.nextInt(1, 100)
    return serviceConfig.responses
        .filter { percent in it.fromPercent..it.toPercent }
        .map { status(it.httpCode) }
        .first()
}

private fun delaySimulate() {
    val percent = Random.nextInt(1, 100)
    val delay = serviceConfig.delays.first { percent in it.fromPercent..it.toPercent }
    sleep(Random.nextLong(delay.minDelay, delay.maxDelay))
}
