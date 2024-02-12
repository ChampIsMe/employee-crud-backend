package com.phil.employeecrudbackend.services.interfaces;


import com.phil.employeecrudbackend.entities.Skill;

import java.util.List;

public interface ISkillService {
  
  Skill create(Skill skill);
  
  Skill findById(Long id);

  List<Skill> findAll();
  
  Skill update(Skill skill);
  
  void delete(Long id);
  
}
