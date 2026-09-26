package com.devshowcase.repository;

import com.devshowcase.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("select avg(f.rating) from Feedback f where f.project.id = :projectId")
    Double averageRatingByProjectId(@Param("projectId") Long projectId);
}
