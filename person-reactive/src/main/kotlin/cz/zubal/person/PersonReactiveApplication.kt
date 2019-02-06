package cz.zubal.person

import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kotlin.experimental.coroutine.EnableCoroutine

@EnableCoroutine
@SpringBootApplication
class PersonReactiveApplication {

    companion object {
        const val PERSON_SERVICE_CONTEXT = "PERSON_SERVICE_CONTEXT"
        const val CONTROLLER_CONTEXT = "CONTROLLER_CONTEXT"
    }

    @Bean(PERSON_SERVICE_CONTEXT)
    fun personServiceContext() = newFixedThreadPoolContext(50, "person-pool")


    @Bean(CONTROLLER_CONTEXT)
    fun requestPool() = newFixedThreadPoolContext(50, "controller-pool")
}

fun main(args: Array<String>) {
    runApplication<PersonReactiveApplication>(*args)
}

