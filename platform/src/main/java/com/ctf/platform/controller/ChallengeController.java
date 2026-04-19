package com.ctf.platform.controller;


import com.ctf.platform.dto.ChallengeDTO;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.repository.ChallengeRepository;
import com.ctf.platform.service.ChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<List<ChallengeDTO>> listChallenges(){
        return ResponseEntity.ok(challengeService.getChallenges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDTO> challengesById(@PathVariable("id") Long idChallenge){
        return ResponseEntity.ok(challengeService.getChallengeByID(idChallenge));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ChallengeDTO>> challengesByCategoryName(@PathVariable("categoryName") String categoryName){
        return ResponseEntity.ok(challengeService.getChallengesByCategoryName(categoryName));
    }

    @PostMapping
    public ResponseEntity<Challenge> postChallenge(@RequestBody @Valid ChallengeDTO dto){
        Challenge createdChallenge = challengeService.createChallenge(dto);
        return ResponseEntity.ok(createdChallenge);
    }

}
