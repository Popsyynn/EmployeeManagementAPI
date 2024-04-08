package com.example.service;

import com.example.exception.EmployeeException;
import com.example.entity.Employee;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeService {



    Employee createEmployee(Employee employee) throws EmployeeException;

    public abstract Collection<Employee> getEmployee();

    public abstract Optional<Employee> getEmployeeById(Long id) throws EmployeeException;

    public abstract void deleteEmployee(Long id) throws EmployeeException;

    public abstract void updateEmployee(Long id , Employee employee) throws EmployeeException;
}
