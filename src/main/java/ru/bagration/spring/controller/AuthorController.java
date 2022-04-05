package ru.bagration.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bagration.spring.dto.author.CreateAuthorDto;
import ru.bagration.spring.service.AuthorService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    ResponseEntity<?> createAuthor(@Valid @RequestBody CreateAuthorDto dto){
        return authorService.createAuthor(dto.getFirstName(), dto.getLastName());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getDetails(@PathVariable String id){
        return authorService.getDetailsById(id);
    }

    @GetMapping("/list")
    ResponseEntity<?> authorsList(@RequestParam(name = "name", defaultValue = "", required = false)String name,
                                  @RequestParam(name = "firstName", required = false)String firstName,
                                  @RequestParam(name = "lastName", required = false)String lastName,
                                  @RequestParam(name = "detailed", required = false, defaultValue = "false")
                                          Boolean detailed,
                                  @PageableDefault(size = 25, sort = {"id"}, direction = Sort.Direction.DESC)
                                          Pageable pageable){
        return authorService.getAuthorsList(name, firstName, lastName, detailed, pageable);
    }

}
