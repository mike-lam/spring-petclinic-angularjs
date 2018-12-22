package org.springframework.samples.petclinic.visit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface VisitRepository extends JpaRepository<Visit, Integer> {

  // use http://localhost:8080/visits/search/pet?petId=8
  @RestResource(rel = "pet", path = "pet")
  List<Visit> findByPetId(@Param("petId") Integer petId);

}
