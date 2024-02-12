package com.phil.employeecrudbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
public class Skill {
  @JsonIgnore
  @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
  List<EmployeeExperience> employeeExperiences;
  @Schema(example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Skill", example = "Java")
  @Column(nullable = false, unique = true)
  private String name;
  @Schema(description = "Creation date and time", example = "2023-11-30T13:40:17.453Z")
  @CreationTimestamp(source = SourceType.DB)
  private LocalDateTime createdAt;
  @Schema(description = "Last modified", example = "2023-11-30T13:40:17.453Z")
  @UpdateTimestamp(source = SourceType.DB)
  private LocalDateTime updatedAt;
}
