package com.phil.employeecrudbackend.dtos.empskill;

import io.swagger.v3.oas.annotations.media.Schema;

public record SkillParams(
    @Schema(description = "Name if skill", example = "Java")
    String name) {
}

