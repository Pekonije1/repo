package com.office.mappers;

import com.office.dto.EmployeeDTO;
import com.office.entities.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Override
    Employee toEntity(EmployeeDTO dto);

    @Override
    EmployeeDTO toDto(Employee entity);

    @Override
    List<Employee> toEntity(List<EmployeeDTO> dtoList);

    @Override
    List<EmployeeDTO> toDto(List<Employee> entityList);
}
