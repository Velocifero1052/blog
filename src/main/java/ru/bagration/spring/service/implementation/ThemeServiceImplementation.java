package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.ThemeService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ThemeServiceImplementation implements ThemeService {

    private final ThemeRepository themeRepository;

    public ResponseEntity<?> addNewTheme(String name, Integer ageCategory){

        if(themeRepository.existsByNameIgnoreCase(name))
            throw new BadRequestException("such name already exists", HttpStatus.CONFLICT.value());

        return ResponseEntity.ok(Collections.singletonMap("anyway", "fuck you!"));
    }

}
