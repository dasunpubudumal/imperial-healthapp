package org.health.imperialhealthapp.repositories;

import org.health.imperialhealthapp.models.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

}
