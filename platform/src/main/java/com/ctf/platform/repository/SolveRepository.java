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

    @Query("SELECT new com.ctf.platform.dto.ScoreboardDTO(s.user.userName, COUNT(s)) " +
            "FROM Solve s GROUP BY s.user.userName ORDER BY COUNT(s) DESC")
    List<ScoreboardDTO> getGlobalScoreboard();
}
