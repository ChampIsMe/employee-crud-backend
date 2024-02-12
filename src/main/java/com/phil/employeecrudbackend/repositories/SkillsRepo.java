package com.phil.employeecrudbackend.repositories;

import com.phil.employeecrudbackend.entities.Skill;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkillsRepo extends ListCrudRepository<Skill, Long>, PagingAndSortingRepository<Skill, Long> {
  Optional<List<Skill>> findAllByIdNotIn(Collection<Long> ids);
  
  Optional<List<Skill>> findAllByNameIn(Collection<String> name);
  
  Optional<List<Skill>> findAllByIdIn(Collection<Long> ids);
  
}
