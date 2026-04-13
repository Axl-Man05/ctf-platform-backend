package com.ctf.platform.dto;

import com.ctf.platform.entity.Challenge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {
    private String title;
    private String description;
    private String difficulty;
    private String categoryName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String flag;

    public ChallengeDTO(Challenge challenge){
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
        this.difficulty = challenge.getDifficulty();
        this.categoryName = challenge.getCategory().getName();
    }
}
