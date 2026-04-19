package com.ctf.platform.service;


import com.ctf.platform.dto.SolveDTO;
import com.ctf.platform.dto.SolveResponseDTO;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.Solve;
import com.ctf.platform.entity.User;
import com.ctf.platform.repository.ChallengeRepository;
import com.ctf.platform.repository.SolveRepository;
import com.ctf.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolveService {
    private final SolveRepository solveRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    public Solve saveSolve (Solve newSolve) {
        return solveRepository.save(newSolve);
    }

    public boolean validateFlag(Challenge challenge, String userSubmittedFlag){
        if(userSubmittedFlag == null) return false;

        String cleanedSubmittedFlag = userSubmittedFlag.trim();
        String correctFlag = challenge.getFlag();

        byte[] correctFlagBytes = correctFlag.getBytes(StandardCharsets.UTF_8);
        byte[] submittedFlagBytes = cleanedSubmittedFlag.getBytes(StandardCharsets.UTF_8);

        return MessageDigest.isEqual(correctFlagBytes, submittedFlagBytes);
    }


    public SolveResponseDTO submitFlag(SolveDTO dto){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long challengeId = dto.getChallengeId();

        User user =  userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("ID not found"));

        if(solveRepository.existsByUserAndChallenge(user, challenge)){
            throw new IllegalStateException("You already solved this challenge");
        }

        if(!validateFlag(challenge, dto.getFlag())){
            throw new IllegalArgumentException("flag doesn't match");
        }

        Solve solve = new Solve();
        solve.setUser(user);
        solve.setSolvedAt(LocalDateTime.now());
        solve.setChallenge(challenge);

        Solve savedSolve = solveRepository.save(solve);

        return new SolveResponseDTO(
                savedSolve.getId(),
                savedSolve.getSolvedAt(),
                challenge.getTitle(),
                user.getUsername());

    }
}
