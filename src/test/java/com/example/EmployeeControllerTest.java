package com.example;

import com.example.controller.EmployeeController;
import com.example.entity.Employee;
import com.example.exception.EmployeeException;
import com.example.exception.EmployeeNotFoundException;
import com.example.service.EmployeeServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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

    @Test
    public void testGetEmployeeByIdReturnsCorrectStatus() throws EmployeeException, Exception {
        Employee employee = new Employee();
        employee.setId(3l);
        employee.setLastName("Enitan");
        employee.setFirstName("Habib");
        employee.setEmail("ola@gmail.com");
        when(service.getEmployeeById(employee.getId())).thenReturn(Optional.of(employee));
        mvc.perform(MockMvcRequestBuilders.get("/employee/{id}" , employee.getId()).content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isFound()).andReturn();

    }

    @Test
    public void testDeleteEmployeeById() throws EmployeeNotFoundException, Exception {
        Long employeeId = 3l;
        doNothing().when(service).deleteEmployee(employeeId);

        mvc.perform(MockMvcRequestBuilders.get("/employee/{id}" , employeeId)
                        .content("deleted").content(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isFound()).andReturn();
    }

    @Test
    public void testUpdateEmployee() throws EmployeeException, Exception {

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1l);
        updatedEmployee.setFirstName("Habib");
        updatedEmployee.setLastName("Olaitan");
        updatedEmployee.setEmail("pop@gmail.com");



        String employeeJson = """
                {"id":1 , "firstName":"Habib" , "lastName":"Olaitan",
                "email":"pop@gmail.com"
                }
                
                
                """;


        when(service.getEmployeeById(anyLong())).thenReturn(Optional.of(updatedEmployee));
        when(service.createEmployee(any())).thenReturn(updatedEmployee);


        mvc.perform(MockMvcRequestBuilders.put("/employee/{id}",updatedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(employeeJson)).andExpect(status().isOk())
                .andExpect(content().string(("updated successfully")));

       /* MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/employee/{id}",updatedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("updated successfully").content(employeeJson)).andExpect(status().isBadRequest()).andReturn();
        String result_str = result.getResponse().getContentAsString();
        System.out.println(result_str);*/

    }


}
