package com.erudio.service;

import com.erudio.exception.ResourceNotFoundException;
import com.erudio.model.Person;
import com.erudio.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonServices service;

    @Mock
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
    public void whenSaveThenReturnSavedPerson() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(firstPerson)).thenReturn(firstPerson);

        Person savedPerson = service.create(firstPerson);

        assertEquals("filipe", savedPerson.getFirstName());
        assertEquals("filipe@gmail.com", savedPerson.getEmail());
    }

    @Test
    public void whenSaveThenThrowsException() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(firstPerson));

        assertThrows(ResourceNotFoundException.class, () -> service.create(firstPerson));

        verify(repository, never()).save(any(Person.class));
    }

    @Test
    public void whenFindAllPeopleThenReturnList(){
        when(repository.findAll()).thenReturn(List.of(firstPerson, secondPerson));

        List<Person> people = service.findAll();

        assertFalse(people.isEmpty());
        assertEquals(2, people.size());
    }

    @Test
    public void whenFindPeopleByIdThenReturnPerson(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(firstPerson));

        Person savedPerson = service.findById(anyLong());

        assertEquals(firstPerson, savedPerson);
    }

    @Test
    public void shouldUpdatePerson(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(firstPerson));
        when(repository.save(firstPerson)).thenReturn(firstPerson);

        firstPerson.setId(1L);
        firstPerson.setFirstName("nick");
        firstPerson.setEmail("nick@gmail.com");

        Person updatedPerson = service.update(firstPerson);

        assertEquals("nick", updatedPerson.getFirstName());
        assertEquals("nick@gmail.com", updatedPerson.getEmail());
    }


    @Test
    public void shouldDeletePerson(){
        doNothing().when(repository).delete(firstPerson);
        when(repository.findById(anyLong())).thenReturn(Optional.of(firstPerson));

        service.delete(anyLong());

        verify(repository, times(1)).delete(firstPerson);
    }

}
