package com.office.service;

import com.office.dto.WorkPositionDTO;
import com.office.exceptions.ServiceException;

import java.util.List;

public interface WorkPositionService {

    /**
     * This method is used to add new work position.
     * After adding new one, method will invoke async method to update cache with new work position object
     *
     * @param workPositionDTO the work position dto
     * @return the work position dto
     * @throws ServiceException the service exception
     */
    WorkPositionDTO addNewWorkPosition(WorkPositionDTO workPositionDTO) throws ServiceException;

    List<WorkPositionDTO> getAllWorkPositions();

    /**
     * This method is used to get work position from cache or database.
     * Firs, it will search cache, if it doesn't exist in cache, than it will try to return it from the database.
     * If returned from database, then work position will be cached
     *
     * @param id the id
     * @return the work position by id
     * @throws ServiceException the service exception
     */
    WorkPositionDTO getWorkPositionById(Long id) throws ServiceException;

    /**
     * This method is used ti check if work position exists in cache or database
     *
     * @param workPositionId the work position id
     * @throws ServiceException the service exception
     */
    void checkIfWorkPositionExists(Long workPositionId) throws ServiceException;

}
