package ru.bagration.spring.dto.publication;

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
    @NotNull(message = "publication.title.require.not.null")
    @NotEmpty(message = "publication.title.require.not.empty")
    @NotBlank(message = "publication.title.require.not.blank")
    private String title;
    @NotNull
    @NotEmpty
    @NotBlank
    private String content;
}
