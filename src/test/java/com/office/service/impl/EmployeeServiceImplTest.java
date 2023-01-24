package com.office.service.impl;


import com.github.database.rider.core.api.dataset.DataSet;
import com.office.config.SpringTestBase;
import com.office.dto.EmployeeDTO;
import com.office.entities.Employee;
import com.office.exceptions.ServiceException;
import com.office.repositories.EmployeeRepository;
import com.office.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class EmployeeServiceImplTest extends SpringTestBase {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DataSet(value = "employee.yml")
    public void shouldGetAllEmployees() {
        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();
        Assert.assertFalse(allEmployees.isEmpty());
    }

    @Test
    @DataSet(value = {"employee.yml", "work_position.yml"})
    public void shouldAddEmployee() throws ServiceException {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("name")
                .lastName("lastName")
                .address("address")
                .startDate(LocalDate.now())
                .email("someEmail@gmail.com")
                .UMCN(1L)
                .bankAccount("bank")
                .phoneNumber("phone")
                .workPositionId(1L)
                .build();


        List<Employee> allEmployees = employeeRepository.findAll();
        allEmployees.forEach(System.out::println);

        int employeeCountBeforeAddingNew = allEmployees.size();
        employeeService.addEmployee(employeeDTO);

        List<Employee> newEmployeeList = employeeRepository.findAll();
        newEmployeeList.forEach(System.out::println);

        int employeeCountAfterAddingNew = newEmployeeList.size();

        Assert.assertTrue(employeeCountAfterAddingNew > employeeCountBeforeAddingNew);

    }
}