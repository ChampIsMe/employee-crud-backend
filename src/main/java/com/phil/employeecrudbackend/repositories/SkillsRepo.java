package com.phil.employeecrudbackend.repositories;

import com.phil.employeecrudbackend.entities.Skill;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillsRepo extends ListCrudRepository<Skill, Long>, PagingAndSortingRepository<Skill, Long> {
  Optional<Skill> findByName(String name);
  
}
