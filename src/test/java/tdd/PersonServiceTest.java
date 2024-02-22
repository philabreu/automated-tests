package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonServiceTest {

    private Person person;
    private IPersonService service;

    @BeforeEach
    public void init() {
        service = new PersonService();
        person = new Person("filipe",
                "bezerra",
                "anywhere",
                "male",
                "test@gmail.com");
    }

    @DisplayName("deve retornar objeto pessoa valido")
    @Test
    public void shouldCreatePersonObject() {
        Person createdPerson = service.createPerson(person);

        assertNotNull(person.getId(), ()-> "id esta nulo");
        assertEquals(person.getFirstName(), createdPerson.getFirstName(), () -> "nome esta incorreto");
        assertEquals(person.getSecondName(), createdPerson.getSecondName(), () -> "sobrenome esta incorreto");
        assertEquals(person.getAddress(), createdPerson.getAddress(), ()-> "endereço esta incorreto");
        assertEquals(person.getSex(), createdPerson.getSex(), ()-> "sexo esta incorreto");
        assertEquals(person.getEmail(), createdPerson.getEmail(), ()-> "email esta incorreto");
    }

}