package com.devshowcase.repository;

import com.devshowcase.entity.Project;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
        select distinct p from Project p
        left join p.technologies t
        where (:technology is null or lower(t.name) = lower(:technology))
        """)
    Page<Project> findByTechnology(@Param("technology") String technology, Pageable pageable);
}
