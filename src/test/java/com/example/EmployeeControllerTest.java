package com.example;

import com.example.controller.EmployeeController;
import com.example.entity.Employee;
import com.example.exception.EmployeeException;
import com.example.service.EmployeeServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    EmployeeServiceImp service;

    @Test
    public void returnAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setId(1l);
        employee.setFirstName("pop");
        employee.setLastName("habib");
        employee.setEmail("pop@gmail.com");
        Employee employee1 = new Employee();
        employee1.setId(2l);
        employee1.setFirstName("Dorcas");
        employee1.setLastName("Ikeoluwa");
        employee1.setEmail("dorcas@gmail.com");
        List<Employee> employees = List.of(employee , employee1);
        //mocking the employeeService get Method
        when(service.getEmployee()).thenReturn(employees);
        mvc.perform(MockMvcRequestBuilders.get("/employee").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isFound()).andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("pop"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("habib"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("pop@gmail.com"));


    }

    @Test
    public void testSavedEmployee() throws EmployeeException, Exception {
        Employee employee = new Employee();
        employee.setId(1l);
        employee.setFirstName("Popoola");
        employee.setLastName("Habib");
        employee.setEmail("popoola@gmail.com");
        String employeeJson = """
                {
                    "id":1 , "firstName":"Popoola"
                        , "lastName":"Habib", "email":"popoola@gmail.com"
                                                                            }
                        """;
        when(service.createEmployee(Mockito.any(Employee.class))).thenReturn(employee);
        mvc.perform((MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(employeeJson)))
                .andExpect(status().isCreated()).andReturn();
    }


}
