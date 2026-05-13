package com.ctf.platform.repository;

import com.ctf.platform.dto.ScoreboardDTO;
import com.ctf.platform.dto.ScoreboardProjection;
import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.Solve;
import com.ctf.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    boolean existsByUserAndChallenge(User user, Challenge challenge);
    List<Solve> findByUser(User user);
   // List<ScoreboardDTO> getGlobalScoreboard();

    @Query("SELECT s.user.userName AS username, SUM(s.challenge.points) AS score " +
            "FROM Solve s " +
            "GROUP BY s.user.userName " +
            "ORDER BY score DESC")
    List<ScoreboardProjection> getScoreboard();

}
