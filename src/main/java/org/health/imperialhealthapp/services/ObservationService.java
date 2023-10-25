package org.health.imperialhealthapp.services;

import org.health.imperialhealthapp.mapper.ObservationMapper;
import org.health.imperialhealthapp.models.GeneralResult;
import org.health.imperialhealthapp.models.Status;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.repositories.ObservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    private final ObservationRepository repository;

    public ObservationService(ObservationRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<GeneralResult<List<ObservationDto>>> listAll() {
        return ResponseEntity.ok(
                GeneralResult.<List<ObservationDto>>builder().status(Status.SUCCESS).data(
                        repository
                                .findAll()
                                .stream()
                                .map(ObservationMapper.INSTANCE::convertToDto)
                                .collect(Collectors.toList())
                ).build()
        );
    }

}
