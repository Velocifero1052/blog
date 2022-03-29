package ru.bagration.spring.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.bagration.spring.utils.ResponseData;

public interface PublicationService {

    ResponseData getAllPublications();

    ResponseEntity<?> getData(String authorName, String themeName, Pageable pageable);

}
