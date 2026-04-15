package com.ctf.platform.controller;

import com.ctf.platform.dto.ScoreboardDTO;
import com.ctf.platform.service.ScoreboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scoreboard")
public class ScoreboardController {
    private final ScoreboardService scoreboardService;

    @GetMapping
    public ResponseEntity<List<ScoreboardDTO>> showTopPlayers(){
        return ResponseEntity.ok(scoreboardService.getTopPlayer());
    }
}
