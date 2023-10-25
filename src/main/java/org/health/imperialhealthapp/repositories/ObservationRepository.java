package org.health.imperialhealthapp.repositories;

import org.health.imperialhealthapp.models.domain.Observation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends CrudRepository<Observation, Integer> {

    List<Observation> findAll();

}
