package com.skillmapshare.api.infra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication()
class InfraTestApplication

fun main(args: Array<String>) {
    runApplication<InfraTestApplication>(*args)
}