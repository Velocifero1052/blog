package ru.bagration.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bagration.spring.dto.publication.NewPublicationDto;
import ru.bagration.spring.service.PublicationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publication")
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping("/list")
    ResponseEntity<?> getData(@RequestParam(name = "author_name", required = false, defaultValue = "")String authorName,
                              @RequestParam(name = "theme_name", required = false, defaultValue = "")String themeName,
                              Pageable pageable){
        return publicationService.getData(authorName, themeName, pageable);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed(){
        return publicationService.getAllPublications();
    }

    @PostMapping
    public ResponseEntity<?> addNewPublication(@Valid @RequestBody NewPublicationDto dto){
        return publicationService.createPublication(dto.getAuthorId(), dto.getThemeId(),
                dto.getTitle(), dto.getContent());
    }

}
