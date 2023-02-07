package com.office.service.impl;

import com.office.cache.EmployeeCache;
import com.office.dto.EmployeeDTO;
import com.office.entities.Employee;
import com.office.exceptions.ServiceException;
import com.office.mappers.EmployeeMapper;
import com.office.repositories.EmployeeRepository;
import com.office.service.EmployeeService;
import com.office.service.WorkPositionService;
import com.office.service.validation.EmployeeValidation;
import com.office.service.validation.ValidationUtils;
import com.utils.ApplicationMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@EnableAsync
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final WorkPositionService workPositionService;
    private final RedisTemplate<String, Employee> redisTemplate;
    private final EmployeeCache employeeCache;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, WorkPositionService workPositionService, RedisTemplate<String, Employee> redisTemplate, EmployeeCache employeeCache) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.workPositionService = workPositionService;
        this.redisTemplate = redisTemplate;
        this.employeeCache = employeeCache;
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
        EmployeeValidation.validateEmployeeInputData(employeeDTO);
        List<EmployeeDTO> allEmployees = getAllEmployees();
        EmployeeValidation.validateEmployeesUniqueFields(employeeDTO, allEmployees);
        workPositionService.checkIfWorkPositionExists(employeeDTO.getWorkPositionId());
        Employee savedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        employeeCache.putInCache(savedEmployee);
        return employeeMapper.toDto(savedEmployee);
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
        EmployeeValidation.checkIfEmployeeIsDisabled(employee);
        return saveDisabledEmployee(employee);
    }

    private EmployeeDTO saveDisabledEmployee(Employee employee) {
        employeeCache.deleteFromCache(employee);
        employee.setDisabled(true);
        employeeCache.putInCache(employee);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    /**
     * {@inheritDoc}
     *
     * @param employeeDTO the employee dto
     * @return
     * @throws ServiceException
     */
    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws ServiceException {
        EmployeeValidation.validateEmployeeInputData(employeeDTO);
        EmployeeDTO employeeToUpdate = getEmployeeById(employeeDTO.getId());

        if (employeeToUpdate.equals(employeeDTO)) {
            log.info(ApplicationMessages.NOTHING_TO_UPDATE_INFO_MSG);
            return employeeDTO;
        }
        List<EmployeeDTO> allEmployees = getEmployeeValidationList(employeeToUpdate);
        EmployeeValidation.validateEmployeesUniqueFields(employeeDTO, allEmployees);

        Employee updatedEmployee = saveUpdatedEmployee(employeeDTO, employeeToUpdate);
        return employeeMapper.toDto(updatedEmployee);
    }

    private Employee saveUpdatedEmployee(EmployeeDTO employeeDTO, EmployeeDTO employeeToUpdate) {
        employeeCache.deleteFromCache(employeeMapper.toEntity(employeeToUpdate));
        Employee updatedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        employeeCache.putInCache(updatedEmployee);
        return updatedEmployee;
    }

    /**
     * This method is used to generate list of all employees except the one that is being updated
     *
     * @param employeeToUpdate
     * @return
     */
    private List<EmployeeDTO> getEmployeeValidationList(EmployeeDTO employeeToUpdate) {
        List<EmployeeDTO> allEmployees = getAllEmployees();
        allEmployees.remove(employeeToUpdate);
        return allEmployees;
    }

    /**
     * {@inheritDoc}
     *
     * @param workPositionId the work position id
     * @return
     */
    @Override
    public List<EmployeeDTO> getEmployeesByWorkPosition(Long workPositionId) {
        List<Employee> employeesByWorkPosition = employeeRepository.findByWorkPosition(workPositionId);
        return employeeMapper.toDto(employeesByWorkPosition);
    }

}
