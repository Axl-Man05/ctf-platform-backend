package com.ctf.platform.service;


import com.ctf.platform.entity.Solve;
import com.ctf.platform.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolveService {
    private final SolveRepository solveRepository;

    public Solve saveSolve (Solve newSolve) {
        return solveRepository.save(newSolve);
    }
}
