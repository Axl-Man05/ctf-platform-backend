package com.ctf.platform.service;

import com.ctf.platform.dto.ChallengeDTO;
import com.ctf.platform.dto.ChallengeResponseDTO;
import com.ctf.platform.entity.Category;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.User;
import com.ctf.platform.repository.CategoryRepository;
import com.ctf.platform.repository.ChallengeRepository;
import com.ctf.platform.repository.SolveRepository;
import com.ctf.platform.repository.UserRepository;
import com.ctf.platform.util.FlagHasher;
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
    private final SolveRepository solveRepository;
    private final UserRepository userRepository;
    private final FlagHasher flagHasher; 

    public ChallengeResponseDTO createChallenge(ChallengeDTO dto) {
        Category finalCategory = categoryRepository.findByName(dto.getCategoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(dto.getCategoryName());
                    return categoryRepository.save(newCategory);
                });

        Challenge newChallenge = Challenge.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .difficulty(dto.getDifficulty())
                .flag(flagHasher.hashFlag(dto.getFlag()))
                .category(finalCategory)
                .points(dto.getPoints())
                .build();

        Challenge savedChallenge = challengeRepository.save(newChallenge);
        return convertToDTO(savedChallenge);
    }

    public List<ChallengeResponseDTO> getChallenges(){
        return challengeRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public ChallengeResponseDTO getChallengeByID(Long idChallenge){
        Challenge challenge = challengeRepository.findById(idChallenge)
                .orElseThrow(() -> new IllegalArgumentException("The Challenge with ID " + idChallenge + " doesn't exist"));
        return convertToDTO(challenge);
    }

    private ChallengeResponseDTO convertToDTO(Challenge challenge){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean solved = false;

        if(auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")){
            Optional<User> userOptional = userRepository.findByEmail(auth.getName());

            if(userOptional.isPresent()){
                solved = solveRepository.existsByUserAndChallenge(userOptional.get(), challenge);
            }
        }

        return ChallengeResponseDTO.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .difficulty(challenge.getDifficulty())
                .categoryName(challenge.getCategory().getName())
                .points(challenge.getPoints())
                .isSolved(solved)
                .build();
    }

    public List<ChallengeResponseDTO> getChallengesByCategoryName(String categoryName){
        return challengeRepository.findByCategoryName(categoryName).stream().map(this::convertToDTO).toList();
    }
}