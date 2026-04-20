package com.ctf.platform.repository;

import com.ctf.platform.dto.ScoreboardDTO;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.Solve;
import com.ctf.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    boolean existsByUserAndChallenge(User user, Challenge challenge);
    List<Solve> findByUser(User user);

    @Query("SELECT new com.ctf.platform.dto.ScoreboardDTO(s.user.username, SUM(s.challenge.points)) " +
            "FROM Solve s " +
            "GROUP BY s.user.username " +
            "ORDER BY SUM(s.challenge.points) DESC")
    List<ScoreboardDTO> getScoreboard();
}
