package com.example.controller;

import com.example.entity.Employee;
import com.example.exception.EmployeeAlreadyExist;
import com.example.exception.EmployeeException;
import com.example.exception.EmployeeNotFoundException;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @RequestMapping(value = "/employee" ,method = RequestMethod.POST)
    public ResponseEntity saveEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExist {
        service.createEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employee" , method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployee(){
        List<Employee> employee = (List<Employee>) service.getEmployee();
        return new ResponseEntity<>(employee , HttpStatus.FOUND);
    }

    @RequestMapping(value = "/employee/email",method = RequestMethod.GET)
    public ResponseEntity<Object> findEmployeeByEmail(@RequestParam String email) throws EmployeeNotFoundException {
        Optional<Employee> employee = service.findEmployeeByEmail(email);
        return new ResponseEntity<>(employee , HttpStatus.FOUND);
    }

    @RequestMapping(value = "/employee" , method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployee(@RequestParam String email) throws EmployeeNotFoundException {
        service.deleteEmployee(email);
        return new ResponseEntity<>("deleted" , HttpStatus.FOUND);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateEmployee(@RequestParam String email , @RequestBody Employee employee) throws EmployeeNotFoundException {
        service.updateEmployee(email ,employee);
        return new ResponseEntity<>("updated successfully" , HttpStatus.OK);
    }
}
