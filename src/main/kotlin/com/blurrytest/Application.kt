package com.blurrytest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
public class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
