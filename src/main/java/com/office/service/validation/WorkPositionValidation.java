package com.office.service.validation;

import com.office.dto.WorkPositionDTO;
import com.office.exceptions.ServiceException;
import com.utils.ApplicationMessages;

import java.util.List;
import java.util.stream.Collectors;

public class WorkPositionValidation {

    public static void validateWorkPositionInputData(WorkPositionDTO workPositionDTO) throws ServiceException {
        ValidationUtils.checkIfObjectExists(workPositionDTO, ApplicationMessages.NOTHING_TO_ADD_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(workPositionDTO.getName(), ApplicationMessages.WORK_POSITION_NAME_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(workPositionDTO.getName(), 128, ApplicationMessages.WORK_POSITION_NAME_TOO_LARGE_ERROR_MSG);
    }

    public static void validateWorkPositionCodeExistence(String code, List<WorkPositionDTO> allWorkPositions) throws ServiceException {
        List<String> allCodes = allWorkPositions.stream().map(WorkPositionDTO::getCode).collect(Collectors.toList());
        if (allCodes.contains(code))
            throw new ServiceException(ApplicationMessages.WORK_POSITION_ALREADY_EXISTS_ERROR_MSG);
    }
}
