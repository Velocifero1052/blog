package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.dto.ThemeListDto;
import ru.bagration.spring.entity.Theme;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.ThemeService;
import ru.bagration.spring.utils.Utility;

import java.util.Collections;
import java.util.stream.Collectors;

import static ru.bagration.spring.utils.ResponseData.response;

@Service
@RequiredArgsConstructor
public class ThemeServiceImplementation implements ThemeService {

    private final ThemeRepository themeRepository;

    public ResponseEntity<?> addNewTheme(String name, Integer ageCategory){

        if(themeRepository.existsByNameIgnoreCase(name))
            throw new BadRequestException("such name already exists", HttpStatus.CONFLICT);

        var theme = new Theme();
        theme.setPublicId(Utility.generateUuid());
        theme.setName(name);
        theme.setAgeCategory(ageCategory);
        themeRepository.save(theme);

        return response(Collections.singletonMap("message", "theme was successfully created"));
    }

    public ResponseEntity<?> allThemes(){
        var themes = themeRepository.findAll();
        return response(themes.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    private ThemeListDto mapToDto(Theme theme){
        var dto = new ThemeListDto();
        dto.setId(theme.getPublicId());
        dto.setName(theme.getName());
        dto.setAgeCategory(theme.getAgeCategory());
        return dto;
    }

}
