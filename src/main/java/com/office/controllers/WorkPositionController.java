package com.office.controllers;

import com.office.dto.WorkPositionDTO;
import com.office.exceptions.ServiceException;
import com.office.service.WorkPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work-position")
@Slf4j
public class WorkPositionController {
    private final WorkPositionService workPositionService;

    public WorkPositionController(WorkPositionService workPositionService) {
        this.workPositionService = workPositionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkPositions() {
        log.info("Getting work positions...");
        return ResponseEntity.ok(workPositionService.getAllWorkPositions());
    }

    @GetMapping("/{workPositionId}")
    public ResponseEntity<?> getWorkPositionById(@PathVariable("workPositionId") Long workPositionId) throws ServiceException {
        log.info("Getting work position...");
        return ResponseEntity.ok(workPositionService.getWorkPositionById(workPositionId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewWorkPosition(@RequestBody WorkPositionDTO workPositionDTO) throws ServiceException {
        log.info("Adding new work position...");
        return ResponseEntity.ok(workPositionService.addNewWorkPosition(workPositionDTO));
    }
}
