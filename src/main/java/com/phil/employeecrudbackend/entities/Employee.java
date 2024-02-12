package com.phil.employeecrudbackend.entities;

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
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
public class Employee {
  @JoinTable(
      name = "employee_experience_link",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "employee_experience_id")
  )
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  List<EmployeeExperience> EmployeeExperiences;
  
  @Schema(example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Employee's first name", example = "Philip")
  @Column(nullable = false)
  private String firstName;
  @Schema(description = "Employee's last name", example = "Okinyi")
  @Column(nullable = false)
  private String lastName;
  @Schema(description = "Contact number", example = "+27715153701")
  @Column(nullable = false)
  private String contactNumber;
  @Schema(description = "Email Address", example = "po@example.com")
  @Column(nullable = false)
  private String email;
  @Schema(description = "Date of birth", example = "2023-11-30")
  @Temporal(TemporalType.DATE)
  private Date dob;
  @Schema(description = "Street Address", example = "Long Street, Apt 404")
  @Column(nullable = false)
  private String address;
  @Schema(description = "City", example = "Cape Town")
  @Column(nullable = false)
  private String city;
  @Schema(description = "Postal code", example = "00100")
  @Column(nullable = false)
  private String postalCode;
  @Schema(description = "Country", example = "South Africa")
  @Column(nullable = false)
  private String country;
  @Schema(description = "Creation date and time", example = "2023-11-30T13:40:17.453Z")
  @CreationTimestamp(source = SourceType.DB)
  private LocalDateTime createdAt;
  @Schema(description = "Last modified", example = "2023-11-30T13:40:17.453Z")
  @UpdateTimestamp(source = SourceType.DB)
  private LocalDateTime updatedAt;
}
