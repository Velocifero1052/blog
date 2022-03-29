package ru.bagration.spring.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ThemeDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Min(0)
    @Max(18)
    @NotNull
    private Integer ageCategory;
}
