package com.office.controllers;

import com.office.dto.EmployeeDTO;
import com.office.exceptions.ServiceException;
import com.office.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employee) throws ServiceException {
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    public ResponseEntity<?> getAllActiveEmployees() {
        return ResponseEntity.ok(employeeService.getAllActiveEmployees());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") Long employeeId) throws ServiceException {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<?> disableEmployee(@PathVariable("id") Long id) throws ServiceException {
        return ResponseEntity.ok(employeeService.disableEmployee(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws ServiceException {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDTO));
    }

    @GetMapping("/by-work-position/{workPositionId}")
    public ResponseEntity<?> getEmployeesByWorkPosition(@PathVariable("workPositionId") Long workPositionId) {
        return ResponseEntity.ok(employeeService.getEmployeesByWorkPosition(workPositionId));
    }
}
