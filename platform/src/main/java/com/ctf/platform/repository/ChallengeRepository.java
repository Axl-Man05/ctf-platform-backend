package com.ctf.platform.repository;

import com.ctf.platform.entity.Challenge;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Override
    @EntityGraph(attributePaths = {"category"})
    List<Challenge> findAll();

    @EntityGraph(attributePaths = {"category"})
    List<Challenge> findByCategoryName(String categoryName);
}
