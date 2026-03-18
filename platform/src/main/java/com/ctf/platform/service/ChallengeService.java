package com.ctf.platform.service;

import com.ctf.platform.entity.Challenge;
import com.ctf.platform.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    public Challenge createChallenge(Challenge newChallenge){
            return challengeRepository.save(newChallenge);

    }

    public List<Challenge> getChallenges(){
        return challengeRepository.findAll();
    }

    public Challenge getChallengeByID(Long idChallenge){
        return  challengeRepository.findById(idChallenge).
                orElseThrow(() -> new IllegalArgumentException("ID doesn't exist"));
    }
}
