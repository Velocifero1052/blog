package ru.bagration.spring.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PublicationService {

    ResponseEntity<?> getAllPublications();

    ResponseEntity<?> getData(String authorName, String themeName, Pageable pageable);

    ResponseEntity<?> createPublication(String authorId, String themeId, String title, String content);

}
