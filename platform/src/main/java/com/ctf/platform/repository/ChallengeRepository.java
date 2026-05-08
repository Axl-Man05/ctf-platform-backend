package com.ctf.platform.repository;

import com.ctf.platform.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Override
    @EntityGraph(attributePaths = {"category"})
    Page<Challenge> findAll(Pageable pageable);

    // 2. Mantenemos el de categoría con List si no vas a paginar esa búsqueda por ahora
    @EntityGraph(attributePaths = {"category"})
    List<Challenge> findByCategoryName(String categoryName);
}
