package com.devshowcase.dto;

import java.util.Set;

public record ProjectResponse(
        Long id, String title, String description, String repositoryUrl,
        String demoUrl, Long profileId, Set<Long> technologyIds,
        Integer upvotes, Double averageRating
) {}
