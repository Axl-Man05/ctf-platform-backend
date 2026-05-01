package com.ctf.platform.service;

import com.ctf.platform.dto.ChallengeDTO;
import com.ctf.platform.entity.Category;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.User;
import com.ctf.platform.repository.CategoryRepository;
import com.ctf.platform.repository.ChallengeRepository;
import com.ctf.platform.repository.SolveRepository;
import com.ctf.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;
    private final SolveRepository solveRepository;
    private final UserRepository userRepository;
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public String hashFlag(String clearTextFlag){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(clearTextFlag.trim().getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error al configurar el algoritmo de seguridad ",e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }



    public Challenge createChallenge(ChallengeDTO dto) {
        // 1. Buscamos o creamos la categoría (esto ya lo tienes bien)
        Category finalCategory = categoryRepository.findByName(dto.getCategoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(dto.getCategoryName());
                    return categoryRepository.save(newCategory);
                });

        // 2. Construimos la entidad (Aquí dto.getFlag() ya funcionará)
        Challenge newChallenge = Challenge.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .difficulty(dto.getDifficulty())
                .flag(hashFlag(dto.getFlag())) // <--- Ya no estará en rojo
                .category(finalCategory) // <-- Usamos el OBJETO finalCategory
                .points(dto.getPoints())
                .build();

        return challengeRepository.save(newChallenge);
    }

    public List<ChallengeDTO> getChallenges(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream().map(ChallengeDTO::new).toList();
    }

    public ChallengeDTO getChallengeByID(Long idChallenge){
        Challenge challenge = challengeRepository.findById(idChallenge)
                .orElseThrow(() -> new IllegalArgumentException("The Challenge with ID " + idChallenge + "doesn't exist"));
        return convertToDTO(challenge);
    }

    private ChallengeDTO convertToDTO(Challenge challenge){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean solved = false;
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            solved = solveRepository.existsByUserAndChallenge(userOptional.get(), challenge);
        }
        return ChallengeDTO.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .difficulty(challenge.getDifficulty())
                .categoryName(challenge.getCategory().getName())
                .points(challenge.getPoints())
                .isSolved(solved) // ¡Aquí está la magia para el Frontend!
                .build();
    }

    public List<ChallengeDTO> getChallengesByCategoryName(String categoryName){
        List<Challenge> filterChallenges = challengeRepository.findByCategoryName(categoryName);

        return filterChallenges.stream().map(ChallengeDTO::new).toList();
    }

}
