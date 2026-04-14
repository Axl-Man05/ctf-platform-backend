package com.ctf.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    String username;
    int totalChallengesSolved;
    List<String> solvedChallenges;

}
