package ru.bagration.spring.service;

import org.springframework.http.ResponseEntity;

public interface ThemeService {

    ResponseEntity<?> addNewTheme(String name, Integer ageCategory);

    ResponseEntity<?> allThemes();

}
