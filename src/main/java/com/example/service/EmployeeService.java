package com.example.service;

import com.example.exception.EmployeeAlreadyExist;
import com.example.exception.EmployeeException;
import com.example.entity.Employee;
import com.example.exception.EmployeeNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeService {



    Employee createEmployee(Employee employee) throws EmployeeAlreadyExist;

    Collection<Employee> getEmployee();

    Optional<Employee> findEmployeeByEmail(String email) throws EmployeeNotFoundException;

    void deleteEmployee(String email) throws EmployeeNotFoundException;

    Optional<Employee> updateEmployee(String email , Employee employee) throws EmployeeNotFoundException;
}
