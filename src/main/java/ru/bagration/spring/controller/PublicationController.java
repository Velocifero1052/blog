package ru.bagration.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bagration.spring.service.PublicationService;

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
        return ResponseEntity.ok(publicationService.getAllPublications());
    }





}
