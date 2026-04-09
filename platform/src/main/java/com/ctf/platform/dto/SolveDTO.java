package com.ctf.platform.dto;

import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolveDTO {
    Long challengeId;
    String flag;
}
