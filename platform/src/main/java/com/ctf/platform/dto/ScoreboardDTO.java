package com.ctf.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreboardDTO {
    private String username;
    private Long score;

    public ScoreboardDTO(String username, Number score) {
        this.username = username;
        this.score = score != null ? score.longValue() : 0L;
    }

}
