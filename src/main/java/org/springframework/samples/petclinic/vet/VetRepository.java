package org.springframework.samples.petclinic.vet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VetRepository extends JpaRepository<Vet, Integer> {
}
