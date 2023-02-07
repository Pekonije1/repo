package com.office.service.impl;

import com.office.cache.WorkPositionCache;
import com.office.dto.WorkPositionDTO;
import com.office.entities.WorkPosition;
import com.office.exceptions.ServiceException;
import com.office.mappers.WorkPositionMapper;
import com.office.repositories.WorkPositionRepository;
import com.office.service.WorkPositionService;
import com.office.service.validation.ValidationUtils;
import com.office.service.validation.WorkPositionValidation;
import com.utils.ApplicationMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class WorkPositionServiceImpl implements WorkPositionService {
    private final WorkPositionRepository workPositionRepository;
    private final WorkPositionMapper workPositionMapper;
    private final WorkPositionCache workPositionCache;

    public WorkPositionServiceImpl(WorkPositionRepository workPositionRepository, WorkPositionMapper workPositionMapper, WorkPositionCache workPositionCache) {
        this.workPositionRepository = workPositionRepository;
        this.workPositionMapper = workPositionMapper;
        this.workPositionCache = workPositionCache;
    }

    @Override
    public WorkPositionDTO addNewWorkPosition(WorkPositionDTO workPositionDTO) throws ServiceException {
        WorkPositionValidation.validateWorkPositionInputData(workPositionDTO);
        generateWorkPositionCode(workPositionDTO);
        WorkPosition workPosition = workPositionRepository.save(workPositionMapper.toEntity(workPositionDTO));
        workPositionCache.putWorkPositionInCache(workPosition);
        return workPositionMapper.toDto(workPosition);
    }

    private void generateWorkPositionCode(WorkPositionDTO workPositionDTO) throws ServiceException {
        String code = workPositionDTO.getName().toUpperCase().trim();
        List<WorkPositionDTO> allWorkPositions = getAllWorkPositions();
        WorkPositionValidation.validateWorkPositionCodeExistence(code, allWorkPositions);
        workPositionDTO.setCode(code);
    }

    @Override
    public List<WorkPositionDTO> getAllWorkPositions() {
        return workPositionMapper.toDto(workPositionRepository.findAll());
    }

    @Override
    public WorkPositionDTO getWorkPositionById(Long id) throws ServiceException {
        ValidationUtils.checkIfObjectExists(id, ApplicationMessages.PARAMETER_MUST_BE_SET);
        WorkPosition workPositionFromCache = workPositionCache.getWorkPositionFromCache(id);
        if (workPositionFromCache != null) {
            log.info("Getting work position with id: " + id + " from cache");
            return workPositionMapper.toDto(workPositionFromCache);
        }
        log.info("Getting work position with id: " + id + " from database.");
        WorkPosition workPosition = workPositionRepository.findById(id).orElseThrow(() -> new ServiceException(ApplicationMessages.WORK_POSITION_DOESNT_EXIST_ERROR_MSG));
        workPositionCache.putWorkPositionInCache(workPosition);
        return workPositionMapper.toDto(workPosition);
    }

    @Override
    public void checkIfWorkPositionExists(Long workPositionId) throws ServiceException {
        try {
            getWorkPositionById(workPositionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(ApplicationMessages.WORK_POSITION_DOESNT_EXIST_ERROR_MSG);
        }
    }
}
