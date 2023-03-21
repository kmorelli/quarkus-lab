package kmorelli.auriga.services.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PersonRepositoryTest {

    @Inject
    PersonRepository personRepository;

    @Test
    public void incluirPerson() {
        var person = new Person();
        person.name = "Rafael";
        person.birth = LocalDate.now();

        personRepository.persist(person);

        Person person2 = new Person();
        person2.name = "Rafael";
        person2.birth = LocalDate.now();

        personRepository.persist(person2);

        var buscaPerson = personRepository.findByName("Rafael");

        System.out.println(buscaPerson.toString());

        assertEquals(person, buscaPerson);
    }

}
