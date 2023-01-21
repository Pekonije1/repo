package com.office.service.impl;

import com.office.dto.EmployeeDTO;
import com.office.entities.Employee;
import com.office.exceptions.ServiceException;
import com.office.mappers.EmployeeMapper;
import com.office.repositories.EmployeeRepository;
import com.office.service.EmployeeService;
import com.utils.ApplicationMessages;
import com.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
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
        validateEmployeeInputData(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        return employeeMapper.toDto(savedEmployee);
    }

    private static void validateEmployeeInputData(EmployeeDTO employeeDTO) throws ServiceException {
        ValidationUtils.checkIfObjectExists(employeeDTO, ApplicationMessages.NOTHING_TO_ADD_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getName(), ApplicationMessages.EMPLOYEE_NAME_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(employeeDTO.getName(), 128, ApplicationMessages.EMPLOYEE_NAME_TOO_LARGE_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getLastName(), ApplicationMessages.EMPLOYEE_LASTNAME_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(employeeDTO.getLastName(), 128, ApplicationMessages.EMPLOYEE_LASTNAME_TOO_LARGE_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getUMCN(), ApplicationMessages.EMPLOYEE_UMCN_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getEmail(), ApplicationMessages.EMPLOYEE_EMAIL_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkIfEmailIsValid(employeeDTO.getEmail(), ApplicationMessages.EMPLOYEE_EMAIL_NOT_VALID_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getStartDate(), ApplicationMessages.EMPLOYEE_START_DATE_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getWorkPositionId(), ApplicationMessages.EMPLOYEE_WORK_POSITION_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getBankAccount(), ApplicationMessages.EMPLOYEE_BANK_ACCOUNT_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(employeeDTO.getBankAccount(), 128, ApplicationMessages.EMPLOYEE_BANK_ACCOUNT_TOO_LARGE_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getPhoneNumber(), ApplicationMessages.EMPLOYEE_PHONE_NUMBER_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(employeeDTO.getPhoneNumber(), 128, ApplicationMessages.EMPLOYEE_PHONE_NUMBER_TOO_LARGE_ERROR_MSG);
        ValidationUtils.checkUnnecessaryStringFieldLength(employeeDTO.getAddress(), 128, ApplicationMessages.EMPLOYEE_ADDRESS_TOO_LARGE_ERROR_MSG);
    }

    /**
     * {@inheritDoc}
     *
     * @param employeeId the employee id
     * @return
     * @throws ServiceException
     */
    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) throws ServiceException {
        ValidationUtils.checkIfObjectExists(employeeId, ApplicationMessages.PARAMETER_MUST_BE_SET);
        return employeeMapper.toDto(employeeRepository.findById(employeeId)
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


}
