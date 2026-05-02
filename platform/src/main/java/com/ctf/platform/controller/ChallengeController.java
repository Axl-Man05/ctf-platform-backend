package com.ctf.platform.controller;


import com.ctf.platform.dto.ChallengeDTO;
import com.ctf.platform.dto.ChallengeResponseDTO;
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
    public ResponseEntity<List<ChallengeResponseDTO>> listChallenges(){
        return ResponseEntity.ok(challengeService.getChallenges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponseDTO> challengesById(@PathVariable("id") Long idChallenge){
        return ResponseEntity.ok(challengeService.getChallengeByID(idChallenge));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ChallengeResponseDTO>> challengesByCategoryName(@PathVariable("categoryName") String categoryName){
        return ResponseEntity.ok(challengeService.getChallengesByCategoryName(categoryName));
    }

    @PostMapping
    public ResponseEntity<ChallengeResponseDTO> postChallenge(@RequestBody @Valid ChallengeDTO dto){
        return ResponseEntity.ok(challengeService.createChallenge(dto));
    }

}
