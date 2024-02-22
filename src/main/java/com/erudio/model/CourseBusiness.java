package com.erudio.model;

import com.erudio.service.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseBusiness implements CourseService {

    private CourseService service;

    public CourseBusiness(CourseService service) {
        this.service = service;
    }

    @Override
    public List<String> retrieve(String student) {
        ArrayList<String> filteredCourses;
        var allCourser = service.retrieve(student);

        filteredCourses = allCourser
                .stream()
                .filter(course -> course.contains("Spring"))
                .collect(Collectors.toCollection(ArrayList::new));


        return filteredCourses;
    }


    @Override
    public void delete(String student) {
        var allCourses = service.retrieve(student);

        allCourses
                .stream()
                .filter(course -> !course.contains("Spring"))
                .forEach(course -> service.delete(course));
    }
}
