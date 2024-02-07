package com.phil.employeecrudbackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Embeddable
public class SkillExperienceKey implements Serializable {
  @Column(name = "employee_id")
  Long employeeId;
  
  @Column(name = "skill_id")
  Long skillId;
}
