package com.ctf.platform.repository;

import com.ctf.platform.entity.Hint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HintRepository extends JpaRepository<Hint, Long> {
    public List<Hint> findByChallengeIdOrderByIdAsc(long challengeId);
}
