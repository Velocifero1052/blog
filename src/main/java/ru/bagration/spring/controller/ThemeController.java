package ru.bagration.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bagration.spring.dto.ThemeDto;
import ru.bagration.spring.service.ThemeService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    ResponseEntity<?> addNewTheme(@RequestBody @Valid ThemeDto dto){
        return themeService.addNewTheme(dto.getName(), dto.getAgeCategory());
    }



}
