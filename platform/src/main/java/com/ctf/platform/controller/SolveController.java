package com.ctf.platform.controller;


import com.ctf.platform.entity.Solve;
import com.ctf.platform.service.SolveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solves")
@RequiredArgsConstructor

public class SolveController {
    private final SolveService solveService;

    @PostMapping
    public ResponseEntity<Solve> postSolve(@RequestBody @Valid Solve solve){
        Solve createSolve = solveService.saveSolve(solve);
        return ResponseEntity.ok(createSolve);
    }
}
