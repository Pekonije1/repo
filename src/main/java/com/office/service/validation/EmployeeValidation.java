package com.office.service.validation;

import com.office.dto.EmployeeDTO;
import com.office.entities.Employee;
import com.office.exceptions.ServiceException;
import com.utils.ApplicationMessages;

import java.util.List;
import java.util.stream.Collectors;


public class EmployeeValidation {

    public static void checkIfEmployeeIsDisabled(Employee employee) throws ServiceException {
        if (employee.isDisabled())
            throw new ServiceException(ApplicationMessages.EMPLOYEE_DISABLED_ERROR_MSG);
    }

    public static void validateEmployeesUniqueFields(EmployeeDTO employeeDTO, List<EmployeeDTO> allEmployees) throws ServiceException {
        List<Long> allUMCNs = allEmployees.stream().map(EmployeeDTO::getUmcn).collect(Collectors.toList());
        List<String> allEmails = allEmployees.stream().map(EmployeeDTO::getEmail).collect(Collectors.toList());
        List<String> allBankAccounts = allEmployees.stream().map(EmployeeDTO::getBankAccount).collect(Collectors.toList());
        List<String> allPhoneNumbers = allEmployees.stream().map(EmployeeDTO::getPhoneNumber).collect(Collectors.toList());

        EmployeeValidation.checkUniqueData(employeeDTO, allUMCNs, allEmails, allBankAccounts, allPhoneNumbers);
    }

    public static void checkUniqueData(EmployeeDTO employeeDTO, List<Long> allUMCNs, List<String> allEmails, List<String> allBankAccounts, List<String> allPhoneNumbers) throws ServiceException {
        checkIfUMCNisDuplicate(employeeDTO, allUMCNs);
        checkIfEmailIsDuplicate(employeeDTO, allEmails);
        checkIfBankAccountIsDuplicate(employeeDTO, allBankAccounts);
        checkIfPhoneNumberIsDuplicate(employeeDTO, allPhoneNumbers);
    }

    private static void checkIfPhoneNumberIsDuplicate(EmployeeDTO employeeDTO, List<String> allPhoneNumbers) throws ServiceException {
        if (allPhoneNumbers.contains(employeeDTO.getPhoneNumber()))
            throw new ServiceException(ApplicationMessages.EMPLOYEES_PHONE_ALREADY_EXISTS_ERROR_MSG);
    }

    private static void checkIfBankAccountIsDuplicate(EmployeeDTO employeeDTO, List<String> allBankAccounts) throws ServiceException {
        if (allBankAccounts.contains(employeeDTO.getBankAccount()))
            throw new ServiceException(ApplicationMessages.EMPLOYEES_BANK_ACCOUNT_ALREADY_EXISTS_ERROR_MSG);
    }

    private static void checkIfEmailIsDuplicate(EmployeeDTO employeeDTO, List<String> allEmails) throws ServiceException {
        if (allEmails.contains(employeeDTO.getEmail()))
            throw new ServiceException(ApplicationMessages.EMPLOYEES_EMAIL_ALREADY_EXISTS_ERROR_MSG);
    }

    private static void checkIfUMCNisDuplicate(EmployeeDTO employeeDTO, List<Long> allUMCNs) throws ServiceException {
        if (allUMCNs.contains(employeeDTO.getUmcn()))
            throw new ServiceException(ApplicationMessages.EMPLOYEES_UMCN_ALREADY_EXISTS_ERROR_MSG);
    }

    public static void validateEmployeeInputData(EmployeeDTO employeeDTO) throws ServiceException {
        ValidationUtils.checkIfObjectExists(employeeDTO, ApplicationMessages.NOTHING_TO_ADD_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getName(), ApplicationMessages.EMPLOYEE_NAME_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(employeeDTO.getName(), 128, ApplicationMessages.EMPLOYEE_NAME_TOO_LARGE_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getLastName(), ApplicationMessages.EMPLOYEE_LASTNAME_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(employeeDTO.getLastName(), 128, ApplicationMessages.EMPLOYEE_LASTNAME_TOO_LARGE_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(employeeDTO.getUmcn(), ApplicationMessages.EMPLOYEE_UMCN_MUST_BE_SET_ERROR_MSG);
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
}
