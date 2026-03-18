package com.ctf.platform.repository;

import com.ctf.platform.entity.Challenge;
import com.ctf.platform.entity.Solve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolveRepository extends JpaRepository<Solve, Long> {
}
