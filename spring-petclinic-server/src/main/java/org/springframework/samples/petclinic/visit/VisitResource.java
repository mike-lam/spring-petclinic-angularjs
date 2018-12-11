package org.springframework.samples.petclinic.visit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class VisitResource {

  private final ClinicService clinicService;

  @Autowired
  public VisitResource(ClinicService clinicService) {
    this.clinicService = clinicService;
  }

  @PostMapping("/owners/{ownerId}/pets/{petId}/visits")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void create(@Valid @RequestBody Visit visit, @PathVariable("petId") int petId) {

    clinicService.findPetById(petId).addVisit(visit);
    clinicService.saveVisit(visit);
  }

  @GetMapping("/owners/{ownerId}/pets/{petId}/visits")
  public Object visits(@PathVariable("petId") int petId) {
    return clinicService.findPetById(petId).getVisits();
  }
}
