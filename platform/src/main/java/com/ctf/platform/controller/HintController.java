package com.ctf.platform.controller;


import com.ctf.platform.dto.HintDTO;
import com.ctf.platform.dto.HintRequestDTO;
import com.ctf.platform.entity.Hint;
import com.ctf.platform.service.HintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hints")
@RequiredArgsConstructor
public class HintController {
    private final HintService hintService;

    @GetMapping("/{id}")
    public ResponseEntity<Hint> hintsByID(@PathVariable Long id){
        return ResponseEntity.ok(hintService.getHintByID(id));
    }

    @PostMapping
    public ResponseEntity<HintDTO> postHint(@RequestBody @Valid HintRequestDTO hintRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hintService.createHint(hintRequest));
    }

    @GetMapping("/challenge/{challengeID}")
    public ResponseEntity<List<HintDTO>> showHintsByChallenge(@PathVariable Long challengeID){
        return ResponseEntity.ok(hintService.getAllHintsForChallenge(challengeID));
    }

}
