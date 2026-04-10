package com.ctf.platform.dto;

import com.ctf.platform.entity.Hint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class HintDTO {
    private Long id;
    private String hintText;

    public HintDTO(Hint hint){
        this.id = hint.getId();
        this.hintText = hint.getHintText();
    }
}
