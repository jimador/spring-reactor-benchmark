package cz.zubal.person.web.rest

import cz.zubal.person.PersonReactiveApplication
import cz.zubal.person.service.PersonService
import cz.zubal.person.util.logger
import io.codearte.jfairy.producer.person.Person
import org.springframework.kotlin.experimental.coroutine.annotation.Coroutine
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
@Coroutine(PersonReactiveApplication.CONTROLLER_CONTEXT)
class PersonResource(private val personService: PersonService) {
    val logger = logger()

    @GetMapping
    suspend fun flux(@RequestParam("number") number: Long): List<Person> {
        logger.info("Starting request from /person")
        val people = mutableListOf<Person>()
        for (i in 1 until number) {
            people.add(personService.getPerson())
        }
        return people.also { logger.info("End of /person request") }
    }


}