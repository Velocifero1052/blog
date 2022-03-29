package ru.bagration.spring.dto;

import lombok.Data;

@Data
public class PublicationDto {
    private String id;
    private String author;
    private String authorId;
    private String title;
    private String theme;
    private String content;
}
