package com.office.service.impl;


import com.github.database.rider.core.api.dataset.DataSet;
import com.office.cache.EmployeeCache;
import com.office.cache.WorkPositionCache;
import com.office.config.SpringTestBase;
import com.office.dto.EmployeeDTO;
import com.office.entities.Employee;
import com.office.exceptions.ServiceException;
import com.office.repositories.EmployeeRepository;
import com.office.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class EmployeeServiceImplTest extends SpringTestBase {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @MockBean
    EmployeeCache employeeCacheMocked;

    @MockBean
    WorkPositionCache workPositionCacheMocked;

    @Test
    @DataSet(value = "employee.yml")
    public void shouldGetAllEmployees() {
        mockCache();
        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();
        Assert.assertFalse(allEmployees.isEmpty());
    }

    @Test
    @DataSet(value = {"employee.yml", "work_position.yml"})
    public void shouldAddEmployee() throws ServiceException {
        mockCache();
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("name")
                .lastName("lastName")
                .address("address")
                .startDate(LocalDateTime.now())
                .email("someEmail@gmail.com")
                .umcn(1L)
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

    private void mockCache() {
        doReturn(null).when(employeeCacheMocked).getEmployeeFromCache(any());
        doReturn(null).when(workPositionCacheMocked).getWorkPositionFromCache(any());
    }
}