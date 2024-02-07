package com.phil.employeecrudbackend.entities;

import com.phil.employeecrudbackend.enums.Seniority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model the JOIN table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
public class SkillExperiences {
  @EmbeddedId
  SkillExperienceKey id;
  
  @ManyToOne
  @MapsId("employeeId")
  @JoinColumn(name = "employee_id")
  Employee employee;
  
  @ManyToOne
  @MapsId("skillId")
  @JoinColumn(name = "skill_id")
  Skill skill;
  
  @Schema(description = "Years of experience", example = "8+")
  @Column(nullable = false)
  private String yrs;
  @Schema(description = "Seniority", example = "Expert")
  @Enumerated(EnumType.STRING)
  private Seniority seniority;
}
