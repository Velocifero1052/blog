package ru.bagration.spring.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AuthorService {

    ResponseEntity<?> createAuthor(String firstName, String lastName);

    ResponseEntity<?> getAuthorsList(String name, String firstName, String lastName, Boolean detailed, Pageable pageable);

    ResponseEntity<?> getDetailsById(String id);

}
