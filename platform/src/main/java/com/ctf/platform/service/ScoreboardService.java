package com.ctf.platform.service;

import com.ctf.platform.dto.ScoreboardDTO;
import com.ctf.platform.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreboardService {
    private final SolveRepository solveRepository;

    public List<ScoreboardDTO> getTopPlayer(){
        return solveRepository.getGlobalScoreboard();
    }

}
