package com.erudio.repository;

import com.erudio.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    private Person firstPerson;

    private Person secondPerson;

    @BeforeEach
    public void init() {
        firstPerson = new Person("filipe",
                "abreu",
                "filipe@gmail.com",
                "anywhere",
                "male");

        secondPerson = new Person("alda",
                "menezes",
                "alda@gmail.com",
                "anywhere",
                "female");
    }

    @Test
    public void shouldReturnSavedPerson() {
        Person savedPerson = repository.save(firstPerson);

        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @Test
    public void shouldFindAllPeople() {
        repository.save(firstPerson);
        repository.save(secondPerson);

        List<Person> people = repository.findAll();

        assertEquals(2, people.size());
    }

    @Test
    public void shouldFindById() {
        repository.save(firstPerson);

        Person savedPerson = repository.findById(firstPerson.getId()).get();

        assertEquals(firstPerson.getId(), savedPerson.getId());
    }

    @Test
    public void shouldFindByEmail() {
        repository.save(firstPerson);

        Person searchedPerson = repository.findByEmail(firstPerson.getEmail()).get();

        assertEquals(firstPerson.getEmail(), searchedPerson.getEmail());
    }

    @Test
    public void shouldUpdatePerson() {
        repository.save(firstPerson);

        Person savedPerson = repository.findById(firstPerson.getId()).get();
        savedPerson.setFirstName("philabreu");
        savedPerson.setEmail("philabreu@gmail.com");

        Person updatedPerson = repository.save(savedPerson);

        assertEquals("philabreu", updatedPerson.getFirstName());
        assertEquals("philabreu@gmail.com", updatedPerson.getEmail());
    }

    @Test
    public void shouldDeletePerson() {
        repository.save(firstPerson);
        repository.deleteById(firstPerson.getId());

        Optional<Person> personOptional = repository.findById(firstPerson.getId());

        assertTrue(personOptional.isEmpty());
    }
}