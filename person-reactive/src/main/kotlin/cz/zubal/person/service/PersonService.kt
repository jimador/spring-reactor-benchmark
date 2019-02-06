package cz.zubal.person.service

import cz.zubal.person.PersonReactiveApplication
import cz.zubal.person.util.logger
import io.codearte.jfairy.Fairy
import io.codearte.jfairy.producer.person.Person
import io.codearte.jfairy.producer.person.PersonProperties
import org.springframework.kotlin.experimental.coroutine.annotation.Coroutine
import org.springframework.stereotype.Service

/**
 * Expensive Call Person service
 */
@Service
@Coroutine(PersonReactiveApplication.PERSON_SERVICE_CONTEXT)
class PersonService(private val fairy: Fairy = Fairy.create()) {

    val logger = logger()

    /**
     * Simulate doing work by bubble sorting a random list of people and getting one from that list
     */
    suspend fun getPerson(): Person {
        logger.info("Starting to fetch person")
        val persons = mutableListOf<Person>()
        // add a random number of people to the list
        for (i in 0..500) {
            persons.add(generatePerson())
        }
        // bubble sort the list by person age and return the first one
        return bubbleSortPeopleByAge(persons)[0].also { logger.info("Ending get Person") }
    }

    /**
     * generate a person with an age between 0 and 9999
     */
    suspend fun generatePerson() : Person {
        return fairy.person(PersonProperties.ageBetween(0, 9999))
    }

    /**
     * dumb n^2 bubble sort
     */
    suspend fun bubbleSortPeopleByAge(people: MutableList<Person>): List<Person> {
        logger.info("Starting bubble sort")
        val n = people.size
        var temp: Person

        for (i in 0 until n) {
            for (j in 1 until n - i) {
                if (people[j - 1].age > people[j].age) {
                    temp = people[j - 1]
                    people[j - 1] = people[j]
                    people[j] = temp
                }
            }
        }

        return people.also { logger.info("Ending bubble sort") }
    }
}