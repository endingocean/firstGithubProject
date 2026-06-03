package com.ainews.portal.repository;

import com.ainews.portal.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n WHERE n.newsDate = :date ORDER BY n.heatScore DESC")
    List<News> findTopByDate(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.newsDate = :date ORDER BY n.heatScore DESC")
    List<News> findByDate(@Param("date") LocalDate date);

    List<News> findByNewsDateOrderByHeatScoreDesc(LocalDate date);
}
