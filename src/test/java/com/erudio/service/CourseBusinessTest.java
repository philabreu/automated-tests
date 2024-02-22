package com.erudio.service;

import com.erudio.model.CourseBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseBusinessTest {

    @Mock
    private CourseService service;

    @InjectMocks
    private CourseBusiness business;
    //    business = new CourseBusiness(service);

    @Captor
    private ArgumentCaptor<String> captor;

    private List<String> courses;

    @BeforeEach
    public void init() {
        courses = Arrays.asList(
                "REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
                "Agile Desmistificado com Scrum, XP, Kanban e Trello",
                "Spotify Engineering Culture Desmistificado",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
                "Docker do Zero à Maestria - Contêinerização Desmistificada",
                "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
                "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
                "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
                "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
                "Microsserviços do 0 com Spring Cloud, Kotlin e Docker"
        );
    }

    @Test
    public void shouldRetriveCourses() {
        when(service.retrieve("filipe")).thenReturn(courses);

        var filteredCourses = business.retrieve("filipe");

        assertEquals(4, filteredCourses.size());
    }

    @Test
    public void shouldThrowException() {
        var list = mock(List.class);

        when(list.get(anyInt())).thenThrow(new RuntimeException("whatever"));

        assertThrows(RuntimeException.class,
                () -> {
                    list.get(anyInt());
                },
                () -> "should have throw an RuntimeException");
    }

    @Test
    @DisplayName("deletar cursos não relacionados a Spring")
    public void shouldDeleteCourseNotRelatedToSpring() {
        given(service.retrieve("filipe")).willReturn(courses);

        business.delete("filipe");

        verify(service).delete("Agile Desmistificado com Scrum, XP, Kanban e Trello");
    }

    @Test
    public void shouldCaptureArgumentsWhenDeleteCourses() {
        courses = Arrays.asList(
                "Agile Desmistificado com Scrum, XP, Kanban e Trello",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker"
        );
        String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";

        given(service.retrieve("filipe")).willReturn(courses);

        business.delete("filipe");

        then(service).should().delete(captor.capture());

        assertEquals(captor.getValue(), agileCourse);
    }
}