package com.example;

import com.example.entity.Employee;
import com.example.exception.EmployeeException;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestingAppApplicationTests {

	@Mock
	EmployeeRepository repository;

	@InjectMocks
	EmployeeServiceImp employeeService;

	@BeforeEach
	public void setup() {

	}

	@Test
	public void testSavedEmployee() throws EmployeeException {
		Employee employee = new Employee();
		employee.setId(1l);
		employee.setFirstName("pop");
		employee.setLastName("habib");
		employee.setEmail("pop@gmail.com");
		when(repository.save(Mockito.any(Employee.class))).thenReturn(employee);

		Employee savedEmployee = employeeService.createEmployee(employee);
		Assertions.assertNotNull(savedEmployee);
		/*employee.setEmail("pop@gmail");
		employee.setId(1l);
		employee.setLastName("hab");
		employee.setLastName("pop");
		when(repository.save(employee)).thenReturn(employee);
		Employee employee1 = employeeService.createEmployee(employee);
		verify(repository , times(1)).save(employee);*/
	}

	@Test
	public void testGetAllEmployee() {
		Employee employee = new Employee();
		employee.setId(1l);
		employee.setEmail("dorkibeauty6@gmail.com");
		employee.setFirstName("Dorcas");
		employee.setLastName("Ike");
		Employee employee1 = new Employee();
		employee1.setId(2l);
		employee1.setEmail("pop@gmail.com");
		employee1.setFirstName("pop");
		employee1.setLastName("habib");
		List<Employee> employees = List.of(employee, employee1);
		when(repository.findAll()).thenReturn(employees);
		List<Employee> employees1 = (List<Employee>) employeeService.getEmployee();
		Assertions.assertNotNull(employees1);
		Assertions.assertSame(employees, employees1);
	}

	@Test
	public void testGetEmployeeById() throws EmployeeException {
		Employee employee = new Employee();
		employee.setId(3l);
		employee.setEmail("pop@gmail.com");
		employee.setFirstName("pop");
		employee.setLastName("ola");
		when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
		Optional<Employee> employee1 = employeeService.getEmployeeById(employee.getId());
		Assertions.assertNotNull(employee1);
		Assertions.assertTrue(employee1.isPresent());
	}

	@Test
	public void testDeleteEmployeeById() throws EmployeeNotFoundException {
		Employee employee = new Employee();
		employee.setId(4l);
		employee.setFirstName("habib");
		employee.setLastName("ola");
		employee.setEmail("pop");
		when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
		employeeService.deleteEmployee(employee.getId());
		verify(repository, times(1)).deleteById(employee.getId());
	}

	@Test
	public void testUpdateEmployeeMethod() throws EmployeeException {
		Employee employee = new Employee();
		employee.setId(1l);
		employee.setFirstName("a");
		employee.setLastName("b");
		employee.setEmail("abc");
		repository.save(employee);

		Employee updatedEmployee = new Employee();
		updatedEmployee.setId(1l);
		updatedEmployee.setFirstName("New-Name");
		updatedEmployee.setLastName("New-Last-Name");
		updatedEmployee.setEmail("New-mail");
		when(repository.findById(employee.getId())).thenReturn(Optional.of(updatedEmployee));
		employeeService.updateEmployee(employee.getId(), updatedEmployee);
		Assertions.assertEquals(updatedEmployee.getEmail(), "New-mail");
		Assertions.assertEquals(updatedEmployee.getFirstName() , "New-Name");
		Assertions.assertEquals(updatedEmployee.getLastName() ,"New-Last-Name");

	}
}
