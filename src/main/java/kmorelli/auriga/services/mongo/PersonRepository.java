package kmorelli.auriga.services.mongo;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class PersonRepository implements PanacheMongoRepository<Person> {

    public Person findByName(String name) {
        return find("name", name).firstResult();
    }
}
