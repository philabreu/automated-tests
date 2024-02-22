package com.erudio.service;

import java.util.List;

public interface CourseService {
    List<String> retrieve(String student);

    void delete(String course);
}