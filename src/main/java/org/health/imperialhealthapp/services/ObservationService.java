package org.health.imperialhealthapp.services;

import org.health.imperialhealthapp.mapper.ObservationMapper;
import org.health.imperialhealthapp.models.GeneralResult;
import org.health.imperialhealthapp.models.Status;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.repositories.ObservationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    public ResponseEntity<GeneralResult<Slice<ObservationDto>>> listAll(Pageable pageable) {
        return ResponseEntity.ok(
                GeneralResult.<Slice<ObservationDto>>builder().status(Status.SUCCESS).data(
                        new SliceImpl<>(
                                repository
                                        .findAll(pageable)
                                        .getContent()
                                        .stream()
                                        .map(ObservationMapper.INSTANCE::convertToDto)
                                        .collect(Collectors.toList())
                        )
                ).build()
        );
    }

}
