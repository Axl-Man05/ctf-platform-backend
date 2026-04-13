package com.ctf.platform.service;

import com.ctf.platform.dto.ChallengeDTO;
import com.ctf.platform.entity.Category;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.repository.CategoryRepository;
import com.ctf.platform.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Challenge> getChallenges(){
        return challengeRepository.findAll();
    }

    public Challenge getChallengeByID(Long idChallenge){
        return  challengeRepository.findById(idChallenge).
                orElseThrow(() -> new IllegalArgumentException("ID doesn't exist"));
    }

    public List<ChallengeDTO> getChallengesByCategoryName(String categoryName){
        List<Challenge> filterChallenges = challengeRepository.findByCategoryName(categoryName);

        return filterChallenges.stream().map(ChallengeDTO::new).toList();
    }

}
