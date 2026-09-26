package com.devshowcase.dto;

public record FeedbackResponse(
        Long id, Integer rating, String comment, String author,
        Long projectId, Double projectAverageRating
) {}
