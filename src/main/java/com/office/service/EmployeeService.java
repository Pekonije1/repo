package com.office.service;

import com.office.dto.EmployeeDTO;
import com.office.exceptions.ServiceException;

import java.util.List;

public interface EmployeeService {
    /**
     * This method is used to add new employee
     *
     * @param employeeDTO the employee dto
     * @return the employee dto
     * @throws ServiceException the service exception
     */
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws ServiceException;

    /**
     * This method is used to get employee by id.
     *
     * @param employeeId the employee id
     * @return the employee by id
     * @throws ServiceException the service exception
     */
    EmployeeDTO getEmployeeById(Long employeeId) throws ServiceException;

    /**
     * This method is used to get all employees.
     *
     * @return the all employees
     */
    List<EmployeeDTO> getAllEmployees();
}
