package com.phil.employeecrudbackend.repositories;

import com.phil.employeecrudbackend.entities.EmployeeExperience;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeExperienceRepo extends ListCrudRepository<EmployeeExperience, Long>, PagingAndSortingRepository<EmployeeExperience, Long> {

}
