package com.office.mappers;

import com.office.dto.WorkPositionDTO;
import com.office.entities.WorkPosition;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkPositionMapper extends EntityMapper<WorkPositionDTO, WorkPosition> {
    @Override
    WorkPosition toEntity(WorkPositionDTO dto);

    @Override
    WorkPositionDTO toDto(WorkPosition entity);

    @Override
    List<WorkPosition> toEntity(List<WorkPositionDTO> dtoList);

    @Override
    List<WorkPositionDTO> toDto(List<WorkPosition> entityList);
}
