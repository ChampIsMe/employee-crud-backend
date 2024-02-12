package com.phil.employeecrudbackend.dtos.empskill;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record SkillCreate(
    @Schema(description = "Name if skill", example = "Java")
    @NotBlank
    String name) {
}