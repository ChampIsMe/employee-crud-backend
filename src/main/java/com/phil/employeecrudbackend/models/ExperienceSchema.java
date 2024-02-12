package com.phil.employeecrudbackend.models;

import com.phil.employeecrudbackend.enums.Seniority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceSchema {
  @Schema(description = "ID", example = "1")
  Long id;
  @Schema(description = "Skill", example = "Java")
  @NotBlank
  String skill;
  @Schema(description = "Years of experience", example = "5+")
  @NotBlank
  String yrs;
  @Schema(description = "Seniority", example = "Expert")
  @NotBlank
  Seniority seniority;
}
