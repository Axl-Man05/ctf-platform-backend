package com.ctf.platform.controller;


import com.ctf.platform.entity.Hint;
import com.ctf.platform.service.HintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.codec.Hints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Hint> postChallenge(@RequestBody @Valid Hint hint){
        Hint createdHint = hintService.createHint(hint);
        return ResponseEntity.ok(createdHint);
    }

}
