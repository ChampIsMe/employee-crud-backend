package com.phil.employeecrudbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.phil.employeecrudbackend.enums.Seniority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Model the JOIN table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
public class EmployeeExperience {
  @JsonBackReference
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "EmployeeExperiences")
  List<Employee> employees;
  
  @ManyToOne(cascade=CascadeType.MERGE)
  @JoinColumn(name = "skill_id")
  Skill skill;
  @Schema(example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Years of experience", example = "8+")
  @Column(nullable = false)
  private String yrs;
  @Schema(description = "Seniority", example = "Expert")
  @Enumerated(EnumType.STRING)
  private Seniority seniority;
  
}
