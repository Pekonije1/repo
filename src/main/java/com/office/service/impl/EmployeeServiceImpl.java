package com.office.service.impl;

import com.office.dto.EmployeeDTO;
import com.office.dto.WorkPositionDTO;
import com.office.entities.Employee;
import com.office.exceptions.ServiceException;
import com.office.mappers.EmployeeMapper;
import com.office.repositories.EmployeeRepository;
import com.office.service.EmployeeService;
import com.office.service.WorkPositionService;
import com.office.service.util.EmployeeValidationUtils;
import com.utils.ApplicationMessages;
import com.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final WorkPositionService workPositionService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, WorkPositionService workPositionService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.workPositionService = workPositionService;
    }

    /**
     * {@inheritDoc}
     *
     * @param employeeDTO the employee dto
     * @return
     * @throws ServiceException
     */
    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws ServiceException {
        EmployeeValidationUtils.validateEmployeeInputData(employeeDTO);
        List<EmployeeDTO> allEmployees = getAllEmployees();
        validateEmployeesUniqueFields(employeeDTO, allEmployees);
        Employee savedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        return employeeMapper.toDto(savedEmployee);
    }

    private void validateEmployeesUniqueFields(EmployeeDTO employeeDTO, List<EmployeeDTO> allEmployees) throws ServiceException {
        List<WorkPositionDTO> allWorkPositionDTOs = workPositionService.getAllWorkPosition();

        List<Long> allUMCNs = allEmployees.stream().map(EmployeeDTO::getUMCN).collect(Collectors.toList());
        List<String> allEmails = allEmployees.stream().map(EmployeeDTO::getEmail).collect(Collectors.toList());
        List<String> allBankAccounts = allEmployees.stream().map(EmployeeDTO::getBankAccount).collect(Collectors.toList());
        List<String> allPhoneNumbers = allEmployees.stream().map(EmployeeDTO::getPhoneNumber).collect(Collectors.toList());
        List<Long> workPositionIds = allWorkPositionDTOs.stream().map(WorkPositionDTO::getId).collect(Collectors.toList());

        EmployeeValidationUtils.checkUniqueData(employeeDTO, allUMCNs, allEmails, allBankAccounts, allPhoneNumbers);
        EmployeeValidationUtils.checkIfWorkPositionExists(employeeDTO, workPositionIds);
    }


    /**
     * {@inheritDoc}
     *
     * @param id the employee id
     * @return
     * @throws ServiceException
     */
    @Override
    public EmployeeDTO getEmployeeById(Long id) throws ServiceException {
        ValidationUtils.checkIfObjectExists(id, ApplicationMessages.PARAMETER_MUST_BE_SET);
        return employeeMapper.toDto(employeeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ApplicationMessages.EMPLOYEE_DOESNT_EXIST_ERROR_MSG)));
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.toDto(employeeRepository.findAll());
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public List<EmployeeDTO> getAllActiveEmployees() {
        return employeeMapper.toDto(employeeRepository.findAllByDisabled(false));
    }

    /**
     * {@inheritDoc}
     *
     * @param id the id
     * @return
     * @throws ServiceException
     */
    @Override
    public EmployeeDTO disableEmployee(Long id) throws ServiceException {
        ValidationUtils.checkIfObjectExists(id, ApplicationMessages.PARAMETER_MUST_BE_SET);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ServiceException(ApplicationMessages.EMPLOYEE_DOESNT_EXIST_ERROR_MSG));
        EmployeeValidationUtils.checkIfEmployeeIsDisabled(employee);
        employee.setDisabled(true);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws ServiceException {
        EmployeeValidationUtils.validateEmployeeInputData(employeeDTO);
        EmployeeDTO employeeToUpdate = getEmployeeById(employeeDTO.getId());

        if (employeeToUpdate.equals(employeeDTO)) {
            log.info(ApplicationMessages.NOTHING_TO_UPDATE_INFO_MSG);
            return employeeDTO;
        }

        List<EmployeeDTO> allEmployees = getEmployeeDTOS(employeeToUpdate);
        validateEmployeesUniqueFields(employeeDTO, allEmployees);
        Employee updatedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        return employeeMapper.toDto(updatedEmployee);
    }

    private List<EmployeeDTO> getEmployeeDTOS(EmployeeDTO employeeToUpdate) {
        List<EmployeeDTO> allEmployees = getAllEmployees();
        allEmployees.remove(employeeToUpdate);
        return allEmployees;
    }

}
