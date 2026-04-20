package com.ctf.platform.service;


import com.ctf.platform.dto.HintDTO;
import com.ctf.platform.dto.HintRequestDTO;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.Hint;
import com.ctf.platform.repository.ChallengeRepository;
import com.ctf.platform.repository.HintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HintService {
    private final HintRepository hintRepository;
    private final ChallengeRepository challengeRepository;
    public HintDTO createHint(HintRequestDTO dto){
        Challenge challenge = challengeRepository.findById(dto.getChallengeId())
                .orElseThrow(() -> new IllegalArgumentException("El reto con ID" + dto.getChallengeId() + "no existe"));

        Hint hint = new Hint();
        hint.setHintText(dto.getHintText());
        hint.setChallenge(challenge);
        Hint savedHint = hintRepository.save(hint);
        return new HintDTO(savedHint);
    }

    public Hint getHintByID(Long idHint){
        return hintRepository.findById(idHint).
                orElseThrow(() -> new IllegalArgumentException("ID doesn't exist"));
    }

    public List<HintDTO> getAllHintsForChallenge(Long challengeID){
        List<Hint> hintList =  hintRepository.findByChallengeIdOrderByIdAsc(challengeID);
        return hintList.stream().map(HintDTO :: new).toList();
    }

}
