package com.example.Coworker.ru.mainService.userService.controller;

import com.example.Coworker.ru.mainService.common.entity.AvailabilityRequest;
import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.userService.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasAuthority('student')")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("api/main/")
public class MainController {

    @Autowired
    private MainPageService mainPageService;


    @GetMapping
    public ResponseEntity<List<Coworking>> getAllCoworkings(){
        return ResponseEntity.status(200).body(mainPageService.getAllCoworkings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coworking> getCoworkingById(@PathVariable UUID id) {
        return ResponseEntity.status(200).body(mainPageService.getCoworkingById(id));
    }

    @PostMapping("/availability")
    public ResponseEntity<List<Coworking>> getAvailableCoworkings(@RequestBody AvailabilityRequest request) {
        List<Coworking> availableCoworkings = mainPageService.findAvailableCoworkings(
                request.getDateTimeStart(),
                request.getDateTimeEnd(),
                request.getCapacity()
        );
        return ResponseEntity.ok(availableCoworkings);
    }
}
