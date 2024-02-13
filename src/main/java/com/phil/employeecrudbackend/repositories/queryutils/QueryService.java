package com.phil.employeecrudbackend.repositories.queryutils;

import com.phil.employeecrudbackend.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryService {
  @PersistenceContext
  EntityManager entityManager;
  
  public List<Employee> employeesSearchByValue(Map<String, Object> employeeParams) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
    Root<Employee> root = cq.from(Employee.class);
    List<Predicate> predicates = employeeParams.entrySet().stream().map(entry -> cb.equal(root.get(entry.getKey()), entry.getValue())).toList();
    cq.where(cb.and(predicates.toArray(new Predicate[0])));
    return entityManager.createQuery(cq).getResultList();
  }
}

