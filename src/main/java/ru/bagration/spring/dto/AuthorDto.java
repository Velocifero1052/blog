package ru.bagration.spring.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AuthorDto {
    private String id;
    private String firstName;
    private String lastName;
    private Long numberOfPublications;
    private Map<String, Long> perTheme;
}
