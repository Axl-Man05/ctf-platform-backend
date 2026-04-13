package com.ctf.platform.repository;

import com.ctf.platform.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findByCategoryName(String name);
}
