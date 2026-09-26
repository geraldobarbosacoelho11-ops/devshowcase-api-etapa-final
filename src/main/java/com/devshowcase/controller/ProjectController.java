package com.devshowcase.controller;

import com.devshowcase.dto.*;
import com.devshowcase.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse create(@Valid @RequestBody ProjectRequest request) {
        return service.create(request);
    }

    @GetMapping
    public Page<ProjectResponse> findAll(
            @RequestParam(required = false) String technology,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAll(technology, page, size);
    }

    @PostMapping("/{id}/feedbacks")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackResponse addFeedback(@PathVariable Long id, @Valid @RequestBody FeedbackRequest request) {
        return service.addFeedback(id, request);
    }

    @PutMapping("/{id}/upvote")
    public ProjectResponse upvote(@PathVariable Long id) {
        return service.upvote(id);
    }
}
