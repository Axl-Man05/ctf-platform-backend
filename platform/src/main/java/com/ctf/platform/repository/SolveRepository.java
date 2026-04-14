package com.ctf.platform.repository;

import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.Solve;
import com.ctf.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    boolean existsByUserAndChallenge(User user, Challenge challenge);
    List<Solve> findByUser(User user);
}
