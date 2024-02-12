package com.phil.employeecrudbackend.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phil.employeecrudbackend.entities.Skill;
import com.phil.employeecrudbackend.dtos.empskill.SkillCreate;
import com.phil.employeecrudbackend.dtos.empskill.SkillParams;
import com.phil.employeecrudbackend.dtos.empskill.SkillUpdate;
import com.phil.employeecrudbackend.services.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "Skills API", description = "CRUD APIs for skills")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/skill")
public class SkillCtrl {
  private final SkillService skillService;
  
  private final ObjectMapper objectMapper;
  
  @Operation(
      summary = "Create a new skill",
      description = "Create a skill with th schema provided")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PostMapping("/create")
  ResponseEntity<Skill> create(@Valid @RequestBody SkillCreate body) {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Skill skill = objectMapper.convertValue(body, Skill.class);
    return ResponseEntity.status(200).body(skillService.create(skill));
  }
  
  @Operation(
      summary = "Retrieve a skill by Id",
      description = "Get a skill object by specifying its id. The response is skill object."
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @GetMapping("/{id}")
  ResponseEntity<Skill> getOne(@PathVariable Long id) {
    Skill skill = skillService.findById(id);
    return ResponseEntity.status(200).body(skill);
  }
  
  //Todo: remove @SuppressWarnings("unused") once pageable and params are used
  @SuppressWarnings("unused")
  @Operation(
      summary = "Retrieve a list of skills",
      description = "Retrieve a list of skills by query"
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Skill.class)), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @GetMapping("/list")
  ResponseEntity<List<Skill>> getList(@ParameterObject Pageable pageable, @Nullable @RequestParam SkillParams params) {
    //todo: implement filtering with params RequestParam
    List<Skill> skills = skillService.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(skills);
  }
  
  @Operation(
      summary = "Update a skill by ID",
      description = "Update a skill by ID"
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Skill.class), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PatchMapping("/update/{id}")
  ResponseEntity<Skill> update(@PathVariable Long id, @Valid @RequestBody SkillUpdate body) {
    if (!Objects.equals(body.id(), id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inconsistent update IDs. Make sure the ID in the URL path is the same as ID passed or ignore ID in your payload");
    }
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Skill skill = objectMapper.convertValue(body, Skill.class);
    skill.setId(id);
    Skill updatedSkill = skillService.update(skill);
    return ResponseEntity.status(HttpStatus.OK).body(updatedSkill);
  }
  
  @Operation(
      summary = "Delete a skill by ID",
      description = "Delete a skill by ID"
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @DeleteMapping("delete/{id}")
  ResponseEntity<Map<String, Long>> deleteOne(@PathVariable Long id) {
    skillService.delete(id);
    return ResponseEntity.status(200).body(Map.of("id", id));
  }
}
