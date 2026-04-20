package com.ctf.platform.dto;

import com.ctf.platform.entity.Challenge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDTO {
    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private String categoryName;
    private int points;
    private boolean isSolved;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String flag;


    public ChallengeDTO(Challenge challenge){
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
        this.difficulty = challenge.getDifficulty();
        this.categoryName = challenge.getCategory().getName();
        this.points = challenge.getPoints();
        this.isSolved = challenge.isSolved();
        this.points = challenge.getPoints();
    }
}
