package com.ctf.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeResponseDTO {
    Long id;
    String title;
    String description;
    String difficulty;
    String categoryName;
    int points;
}
