package ru.bagration.spring.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class NewPublicationDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String authorId;
    @NotNull
    @NotEmpty
    @NotBlank
    private String themeId;
    @NotNull
    @NotEmpty
    @NotBlank
    private String title;
    @NotNull
    @NotEmpty
    @NotBlank
    private String content;
}
