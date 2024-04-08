package com.example.service;

import com.example.exception.EmployeeAlreadyExist;
import com.example.exception.EmployeeException;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Employee> employee1 = Optional.ofNullable(repository.findByEmail(employee.getEmail()));
        if (employee1.isPresent()){
            throw new EmployeeAlreadyExist();
        }
        repository.save(employee);
        return employee;
    }

    @Override
    public Collection<Employee> getEmployee() {
        List<Employee> employees = new ArrayList<>();
        employees = repository.findAll();
        return employees;
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) throws EmployeeException {
        if (!repository.findById(id).isPresent()){
            throw new EmployeeException();
        }
        Optional<Employee> employee =  repository.findById(id);
        return employee;

        }

    @Override
    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        if (!repository.findById(id).isPresent()){
            throw new EmployeeNotFoundException();
        }
        repository.deleteById(id);

    }

    @Override
    public void updateEmployee(Long id , Employee employee) throws EmployeeException {
    if (!repository.findById(id).isPresent()){
        throw new EmployeeException();
    }
    repository.deleteById(id);
    Employee employee1 = new Employee();
    employee1.setEmail(employee.getEmail());
    employee1.setId(employee.getId());
    employee1.setFirstName(employee.getFirstName());
    employee1.setLastName(employee.getLastName());
    repository.save(employee1);

    }
}
