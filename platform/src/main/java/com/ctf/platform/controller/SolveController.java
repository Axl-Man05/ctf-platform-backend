package com.ctf.platform.controller;


import com.ctf.platform.dto.SolveDTO;
import com.ctf.platform.dto.SolveResponseDTO;
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
    public ResponseEntity<SolveResponseDTO> postSolve(@RequestBody @Valid SolveDTO dto){
        return ResponseEntity.ok(solveService.submitFlag(dto));
    }
}
