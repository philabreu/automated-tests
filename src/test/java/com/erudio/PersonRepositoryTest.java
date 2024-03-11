package com.erudio;

import com.erudio.model.Person;
import com.erudio.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    public void shouldReturnSavedPerson() {
        Person person = new Person("filipe",
                "abreu",
                "test@gmail.com",
                "anywhere",
                "male");

        Person savedPerson = repository.save(person);

        assertNotNull(savedPerson);
    }
}
