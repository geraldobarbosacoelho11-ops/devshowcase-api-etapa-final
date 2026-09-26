package com.devshowcase.service;

import com.devshowcase.dto.*;
import com.devshowcase.entity.*;
import com.devshowcase.exception.ResourceNotFoundException;
import com.devshowcase.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projects;
    private final ProfileRepository profiles;
    private final TechnologyRepository technologies;
    private final FeedbackRepository feedbacks;

    public ProjectService(ProjectRepository projects, ProfileRepository profiles,
                          TechnologyRepository technologies, FeedbackRepository feedbacks) {
        this.projects = projects;
        this.profiles = profiles;
        this.technologies = technologies;
        this.feedbacks = feedbacks;
    }

    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        Profile profile = profiles.findById(request.profileId())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado: " + request.profileId()));

        Set<Long> requestedIds = request.technologyIds() == null ? Set.of() : request.technologyIds();
        List<Technology> found = technologies.findAllById(requestedIds);
        Set<Long> foundIds = found.stream().map(Technology::getId).collect(Collectors.toSet());

        Set<Long> missingIds = new HashSet<>(requestedIds);
        missingIds.removeAll(foundIds);
        if (!missingIds.isEmpty()) {
            throw new ResourceNotFoundException("Tecnologia(s) não encontrada(s): " + missingIds);
        }

        Project project = Project.builder()
                .title(request.title().trim())
                .description(blankToNull(request.description()))
                .repositoryUrl(blankToNull(request.repositoryUrl()))
                .demoUrl(blankToNull(request.demoUrl()))
                .profile(profile)
                .technologies(new HashSet<>(found))
                .upvotes(0)
                .averageRating(0.0)
                .build();

        return toResponse(projects.save(project));
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponse> findAll(String technology, int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page deve ser maior ou igual a 0");
        if (size < 1 || size > 100) throw new IllegalArgumentException("size deve estar entre 1 e 100");

        String filter = blankToNull(technology);
        return projects.findByTechnology(filter, PageRequest.of(page, size, Sort.by("id").descending()))
                .map(this::toResponse);
    }

    @Transactional
    public FeedbackResponse addFeedback(Long projectId, FeedbackRequest request) {
        Project project = findProject(projectId);

        Feedback feedback = Feedback.builder()
                .rating(request.rating())
                .comment(request.comment().trim())
                .author(request.author() == null || request.author().isBlank() ? "Anônimo" : request.author().trim())
                .project(project)
                .build();

        Feedback saved = feedbacks.save(feedback);
        Double average = feedbacks.averageRatingByProjectId(projectId);
        project.setAverageRating(average == null ? 0.0 : Math.round(average * 100.0) / 100.0);
        projects.save(project);

        return new FeedbackResponse(saved.getId(), saved.getRating(), saved.getComment(),
                saved.getAuthor(), projectId, project.getAverageRating());
    }

    @Transactional
    public ProjectResponse upvote(Long projectId) {
        Project project = findProject(projectId);
        project.setUpvotes(project.getUpvotes() + 1);
        return toResponse(projects.save(project));
    }

    private Project findProject(Long id) {
        return projects.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado: " + id));
    }

    private ProjectResponse toResponse(Project project) {
        Set<Long> technologyIds = project.getTechnologies().stream()
                .map(Technology::getId)
                .collect(Collectors.toSet());

        return new ProjectResponse(project.getId(), project.getTitle(), project.getDescription(),
                project.getRepositoryUrl(), project.getDemoUrl(), project.getProfile().getId(),
                technologyIds, project.getUpvotes(), project.getAverageRating());
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
