package org.springframework.samples.petclinic.vet;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class VetResource {

  private final ClinicService clinicService;

  @Autowired
  public VetResource(ClinicService clinicService) {
    this.clinicService = clinicService;
  }

  @GetMapping("/vets")
  public Collection<Vet> showResourcesVetList() {
    return this.clinicService.findVets();
  }
}
