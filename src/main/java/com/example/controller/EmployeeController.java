package com.example.controller;

import com.example.entity.Employee;
import com.example.exception.EmployeeException;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @RequestMapping(value = "/employee" ,method = RequestMethod.POST)
    public ResponseEntity saveEmployee(@RequestBody Employee employee) throws EmployeeException {
        service.createEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employee" , method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployee(){
        List<Employee> employee = (List<Employee>) service.getEmployee();
        return new ResponseEntity<>(employee , HttpStatus.FOUND);
    }

    @RequestMapping(value = "/employee/{id}",method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) throws EmployeeException {
        Optional<Employee> employee = service.getEmployeeById(id);
        return new ResponseEntity<>(employee , HttpStatus.FOUND);
    }

    @RequestMapping(value = "/employee/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) throws EmployeeException {
        service.deleteEmployee(id);
        return new ResponseEntity<>("deleted" , HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id , @RequestBody Employee employee) throws EmployeeException {
        service.updateEmployee(id ,employee);
        return new ResponseEntity<>("updated successfully" , HttpStatus.OK);
    }
}
