package com.phil.employeecrudbackend.dtos.empskill;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SkillUpdate(
    @Schema(example = "1")
    @Positive
    Long id,
    @Schema(description = "Name if skill", example = "Java")
    @NotBlank
    String name) {
}

