package com.ctf.platform.controller;


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
    public ResponseEntity<List<Challenge>> listChallenges(){
        return ResponseEntity.ok(challengeService.getChallenges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Challenge> challengesById(@PathVariable("id") Long idChallenge){
        return ResponseEntity.ok(challengeService.getChallengeByID(idChallenge));
    }


    @PostMapping
    public ResponseEntity<Challenge> postChallenge(@RequestBody @Valid Challenge challenge){
        Challenge createdChallenge = challengeService.createChallenge(challenge);
        return ResponseEntity.ok(createdChallenge);
    }

}
