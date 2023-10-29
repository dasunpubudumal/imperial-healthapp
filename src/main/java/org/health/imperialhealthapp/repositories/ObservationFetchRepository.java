package org.health.imperialhealthapp.repositories;

import org.health.imperialhealthapp.models.domain.Observation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ObservationFetchRepository extends PagingAndSortingRepository<Observation, UUID> {


}