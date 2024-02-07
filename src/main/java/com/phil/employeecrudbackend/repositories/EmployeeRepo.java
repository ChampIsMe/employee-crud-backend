package com.phil.employeecrudbackend.repositories;

import com.phil.employeecrudbackend.entities.Employee;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends ListCrudRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {

}
