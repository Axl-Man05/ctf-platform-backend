package com.ctf.platform.service;

import com.ctf.platform.dto.ChallengeDTO;
import com.ctf.platform.entity.Category;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.repository.CategoryRepository;
import com.ctf.platform.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;


    public Challenge createChallenge(ChallengeDTO dto){
        Category finalCategory = categoryRepository.
                findByName(dto.getCategoryName()).orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(dto.getCategoryName());
                    return categoryRepository.save(newCategory);
                });

        Challenge newChallenge =  Challenge.builder().title(dto.getTitle())
                .description(dto.getDescription()).difficulty(dto.getDifficulty())
                .flag(dto.getFlag()).category(finalCategory).build();
            return challengeRepository.save(newChallenge);
    }

    public List<ChallengeDTO> getChallenges(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream().map(ChallengeDTO::new).toList();
    }

    public Challenge getChallengeByID(Long idChallenge){

    //    Challenge challengeById = challengeRepository.findById(idChallenge).orElseThrow(() -> new IllegalArgumentException("ID doesn't exist"));
        return  challengeRepository.findById(idChallenge).
                orElseThrow(() -> new IllegalArgumentException("ID doesn't exist"));
    }

    public List<ChallengeDTO> getChallengesByCategoryName(String categoryName){
        List<Challenge> filterChallenges = challengeRepository.findByCategoryName(categoryName);

        return filterChallenges.stream().map(ChallengeDTO::new).toList();
    }

}
