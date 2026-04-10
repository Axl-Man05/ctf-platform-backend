package com.ctf.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class SolveResponseDTO {
    private Long id;
    private LocalDateTime solvedAt;
    private String nameChallenge;
    private String userName;
}
