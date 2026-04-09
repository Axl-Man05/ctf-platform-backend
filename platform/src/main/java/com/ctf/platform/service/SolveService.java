package com.ctf.platform.service;


import com.ctf.platform.dto.SolveDTO;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.Solve;
import com.ctf.platform.entity.User;
import com.ctf.platform.repository.ChallengeRepository;
import com.ctf.platform.repository.SolveRepository;
import com.ctf.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

    public Solve submitFlag(SolveDTO dto){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuario not found"));
        Long challengeId = dto.getChallengeId();
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("ID not found"));

        if(!challenge.getFlag().equals(dto.getFlag())){
            throw new IllegalArgumentException("flag doesn't match");
        }
        if(solveRepository.existsByUserAndChallenge(user, challenge)){
            throw new IllegalStateException("You already solved this challenge");
        }
        Solve solve = new Solve();
        solve.setUser(user);
        solve.setSolvedAt(LocalDateTime.now());
        solve.setChallenge(challenge);

        return solveRepository.save(solve);
    }
}
