package com.devshowcase.service;

import com.devshowcase.dto.ProfileRequest;
import com.devshowcase.dto.ProfileResponse;
import com.devshowcase.entity.Profile;
import com.devshowcase.exception.BusinessException;
import com.devshowcase.exception.ResourceNotFoundException;
import com.devshowcase.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public ProfileResponse create(ProfileRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException("Já existe um perfil com este e-mail.");
        }

        Profile profile = Profile.builder()
                .name(request.name().trim())
                .email(request.email().trim().toLowerCase())
                .bio(blankToNull(request.bio()))
                .githubUrl(blankToNull(request.githubUrl()))
                .linkedinUrl(blankToNull(request.linkedinUrl()))
                .build();

        return toResponse(repository.save(profile));
    }

    public ProfileResponse findById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado: " + id));
    }

    private ProfileResponse toResponse(Profile profile) {
        return new ProfileResponse(profile.getId(), profile.getName(), profile.getEmail(),
                profile.getBio(), profile.getGithubUrl(), profile.getLinkedinUrl());
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
