package com

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PrinciplesApplication

fun main(args: Array<String>) {
	SpringApplication.run(PrinciplesApplication::class.java, *args)
}
