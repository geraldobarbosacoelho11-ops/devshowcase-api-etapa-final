package com.devshowcase.service;

import com.devshowcase.dto.TechnologyRequest;
import com.devshowcase.dto.TechnologyResponse;
import com.devshowcase.entity.Technology;
import com.devshowcase.exception.BusinessException;
import com.devshowcase.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {
    private final TechnologyRepository repository;

    public TechnologyService(TechnologyRepository repository) {
        this.repository = repository;
    }

    public TechnologyResponse create(TechnologyRequest request) {
        String name = request.name().trim();
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException("A tecnologia já está cadastrada: " + name);
        }

        Technology technology = Technology.builder().name(name).build();
        return toResponse(repository.save(technology));
    }

    public List<TechnologyResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    private TechnologyResponse toResponse(Technology technology) {
        return new TechnologyResponse(technology.getId(), technology.getName());
    }
}
