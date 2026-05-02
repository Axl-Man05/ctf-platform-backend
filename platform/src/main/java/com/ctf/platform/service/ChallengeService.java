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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
                .flag(hashFlag(dto.getFlag()))
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
                .orElseThrow(() -> new IllegalArgumentException("The Challenge with ID " + idChallenge + "doesn't exist"));
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
