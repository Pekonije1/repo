package com.office.service.impl;

import com.office.dto.WorkPositionDTO;
import com.office.entities.WorkPosition;
import com.office.exceptions.ServiceException;
import com.office.mappers.WorkPositionMapper;
import com.office.repositories.WorkPositionRepository;
import com.office.service.WorkPositionService;
import com.utils.ApplicationMessages;
import com.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WorkPositionServiceImpl implements WorkPositionService {
    private final WorkPositionRepository workPositionRepository;
    private final WorkPositionMapper workPositionMapper;

    public WorkPositionServiceImpl(WorkPositionRepository workPositionRepository, WorkPositionMapper workPositionMapper) {
        this.workPositionRepository = workPositionRepository;
        this.workPositionMapper = workPositionMapper;
    }

    @Override
    public WorkPositionDTO addNewWorkPosition(WorkPositionDTO workPositionDTO) throws ServiceException {
        validateWorkPositionInputData(workPositionDTO);
        generateWorkPositionCode(workPositionDTO);
        WorkPosition workPosition = workPositionRepository.save(workPositionMapper.toEntity(workPositionDTO));
        return workPositionMapper.toDto(workPosition);
    }

    private static void generateWorkPositionCode(WorkPositionDTO workPositionDTO) {
        workPositionDTO.setCode(workPositionDTO.getName().toUpperCase().trim());
    }

    private static void validateWorkPositionInputData(WorkPositionDTO workPositionDTO) throws ServiceException {
        ValidationUtils.checkIfObjectExists(workPositionDTO, ApplicationMessages.NOTHING_TO_ADD_ERROR_MSG);
        ValidationUtils.checkIfObjectExists(workPositionDTO.getName(), ApplicationMessages.WORK_POSITION_NAME_MUST_BE_SET_ERROR_MSG);
        ValidationUtils.checkStringLength(workPositionDTO.getName(), 128, ApplicationMessages.WORK_POSITION_NAME_TOO_LARGE_ERROR_MSG);
    }

    @Override
    public List<WorkPositionDTO> getAllWorkPositionDTOs() {
        return workPositionMapper.toDto(workPositionRepository.findAll());
    }

    @Override
    public WorkPositionDTO getWorkPositionById(Long id) throws ServiceException {
        ValidationUtils.checkIfObjectExists(id, ApplicationMessages.PARAMETER_MUST_BE_SET);
        return workPositionMapper.toDto(workPositionRepository.findById(id).orElseThrow(() -> new ServiceException(ApplicationMessages.WORK_POSITION_DOESNT_EXIST_ERROR_MSG)));
    }
}
