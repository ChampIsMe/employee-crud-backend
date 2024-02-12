package com.phil.employeecrudbackend.services;

import com.phil.employeecrudbackend.entities.Skill;
import com.phil.employeecrudbackend.repositories.SkillsRepo;
import com.phil.employeecrudbackend.services.interfaces.ISkillService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class SkillService implements ISkillService {
  private final SkillsRepo skillsRepo;
  
  @Transactional
  @Override
  public Skill create(Skill skill) {
    return skillsRepo.save(skill);
  }
  
  @Override
  public Skill findById(Long id) {
    return skillsRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No skill record with such ID"));
  }
  
  
  @Override
  public List<Skill> findAll() {
    return skillsRepo.findAll();
  }
  
  @Transactional
  @Override
  public Skill update(Skill skill) {
    return skillsRepo.findById(skill.getId()).map(device1 -> skillsRepo.save(skill)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No skill record with such ID"));
  }
  
  @Transactional
  @Override
  public void delete(Long id) {
    skillsRepo.deleteById(id);
  }
}
