package com.tomas.devlogs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DevlogsApplication

fun main(args: Array<String>) {
	runApplication<DevlogsApplication>(*args)
}
