package com.ctf.platform.repository;

import com.ctf.platform.entity.Hint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HintRepository extends JpaRepository<Hint, Long> {
}
