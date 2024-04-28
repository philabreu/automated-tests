package com.erudio.controller;

import com.erudio.exception.ResourceNotFoundException;
import com.erudio.model.Person;
import com.erudio.service.PersonServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class PersonControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServices service;

    @Autowired
    private ObjectMapper mapper;

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
    public void shouldSavePerson() throws Exception {
        when(service.create(any(Person.class))).thenAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firstPerson)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(firstPerson.getFirstName())))
                .andExpect(jsonPath("$.email", is(firstPerson.getEmail())));
    }

    @Test
    public void shouldFindAllPeople() throws Exception {
        List<Person> people = List.of(firstPerson, secondPerson);

        when(service.findAll()).thenReturn(people);

        ResultActions response = mockMvc.perform(get("/person"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(people.size())));
    }

    @Test
    public void whenFindPersonByIdThenReturnPerson() throws Exception {
        when(service.findById(1L)).thenReturn(firstPerson);

        ResultActions response = mockMvc.perform(get("/person/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(firstPerson.getFirstName())))
                .andExpect(jsonPath("$.email", is(firstPerson.getEmail())));
    }

    @Test
    public void whenFindPersonByIdThenThrowsException() throws Exception {
        when(service.findById(1L)).thenThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/person/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenUpdatePersonThenReturnPerson() throws Exception {
        when(service.findById(1L)).thenReturn(firstPerson);
        when(service.update(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Person updatedPerson = new Person("nick",
                "abreu",
                "nick@gmail.com",
                "anywhere",
                "male");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
    }

    @Test
    public void whenUpdatePersonThenThrowsException() throws Exception {
        when(service.findById(1L)).thenThrow(ResourceNotFoundException.class);
        when(service.update(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(1));

        Person updatedPerson = new Person("nick",
                "abreu",
                "nick@gmail.com",
                "anywhere",
                "male");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        doNothing().when(service).delete(1L);

        ResultActions response = mockMvc.perform(delete("/person/{id}", 1L));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}