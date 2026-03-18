package com.ctf.platform.service;


import com.ctf.platform.entity.Hint;
import com.ctf.platform.repository.HintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HintService {
    private final HintRepository hintRepository;
    public Hint createHint(Hint newHint){
        return hintRepository.save(newHint);
    }

    public Hint getHintByID(Long idHint){
        return hintRepository.findById(idHint).
                orElseThrow(() -> new IllegalArgumentException("ID doesn't exist"));


    }


}
