package com.ctf.platform.service;

import com.ctf.platform.dto.UserProfileDTO;
import com.ctf.platform.entity.Solve;
import com.ctf.platform.entity.User;
import com.ctf.platform.repository.SolveRepository;
import com.ctf.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final SolveRepository solveRepository;
    public UserProfileDTO getMyProfile(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        List<Solve> solvedChallenges = solveRepository.findByUser(user);
        List<String> titles = solvedChallenges.stream()
                .map(solve -> solve.getChallenge().getTitle()).toList();

        return UserProfileDTO.builder().username(user.getUsername())
                .totalChallengesSolved(solvedChallenges.size())
                .solvedChallenges(titles)
                .build();
    }
}
