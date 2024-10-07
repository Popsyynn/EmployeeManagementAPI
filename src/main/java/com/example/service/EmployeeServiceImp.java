package com.example.service;

import com.example.exception.EmployeeAlreadyExist;
import com.example.exception.EmployeeException;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    EmployeeRepository repository;


    @Override
    public Employee createEmployee(Employee employee) throws EmployeeAlreadyExist {
        if (repository.findByEmail(employee.getEmail()).isPresent()){
            throw new EmployeeAlreadyExist();
        }
        return repository.save(employee);
    }

    @Override
    public Collection<Employee> getEmployee() {
        List<Employee> employees = repository.findAll();
        return employees;
    }

    @Override
    public Optional<Employee> findEmployeeByEmail(String email) throws EmployeeNotFoundException{

        Optional<Employee> employee = Optional.ofNullable(repository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException()));
        return employee;

        }

    @Override
    public void deleteEmployee(String email) throws EmployeeNotFoundException{
        if (repository.findByEmail(email).isPresent()){
            throw new EmployeeNotFoundException();
        }else{
            repository.delete(repository.findByEmail(email).get());
        }
    }

    @Override
    @Transactional
    public Optional<Employee> updateEmployee(String email , Employee employee) throws EmployeeNotFoundException {
        Employee employee1 = repository.findByEmail(employee.getEmail()).orElseThrow(() -> new EmployeeNotFoundException());
            employee1.setEmail(employee.getEmail());
            employee1.setFirstName(employee.getFirstName());
            employee1.setLastName(employee.getLastName());
        return Optional.of(repository.save(employee1));
    }
}
