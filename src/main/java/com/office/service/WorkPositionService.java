package com.office.service;

import com.office.dto.WorkPositionDTO;
import com.office.exceptions.ServiceException;

import java.util.List;

public interface WorkPositionService {
    WorkPositionDTO addNewWorkPosition(WorkPositionDTO workPositionDTO) throws ServiceException;

    List<WorkPositionDTO> getAllWorkPositionDTOs();

    WorkPositionDTO getWorkPositionById(Long id) throws ServiceException;

}
