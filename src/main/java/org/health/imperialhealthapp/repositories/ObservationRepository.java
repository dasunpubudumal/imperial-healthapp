package org.health.imperialhealthapp.repositories;

import org.health.imperialhealthapp.models.domain.Observation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationRepository extends CrudRepository<Observation, Integer> {

    Slice<Observation> findAll(Pageable pageable);

}
