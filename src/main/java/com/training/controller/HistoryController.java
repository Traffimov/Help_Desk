package com.training.controller;

import com.training.dto.history.HistoryDto;
import com.training.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/tickets/{id}/histories")
    public ResponseEntity<List<HistoryDto>> getAllHistory(@PathVariable Long id) {
        return ResponseEntity.ok(historyService.getAllHistory(id));
    }
}
