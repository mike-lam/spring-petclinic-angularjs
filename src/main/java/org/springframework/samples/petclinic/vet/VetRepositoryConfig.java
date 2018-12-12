package org.springframework.samples.petclinic.vet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class VetRepositoryConfig {

  @Autowired
  private VetRepository vetRepository;
  @Autowired
  private SpecialtyRepository specialtyRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    preloadSpecialties();
    preloadVets();
  }

  private void preloadVets() {
    Vet vet;
    vet = new Vet(1, "James", "Carter");
    vetRepository.save(vet);
    vet = new Vet(2, "Helen", "Leary");
    vet.addSpecialty(specialtyRepository.findById(1).get());
    vetRepository.save(vet);
    vet = new Vet(3, "Linda", "Douglas");
    vet.addSpecialty(specialtyRepository.findById(2).get());
    vet.addSpecialty(specialtyRepository.findById(3).get());
    vetRepository.save(vet);
    vet = new Vet(4, "Rafael", "Ortega");
    vet.addSpecialty(specialtyRepository.findById(2).get());
    vetRepository.save(vet);
    vet = new Vet(5, "Henry", "Stevens");
    vetRepository.save(vet);
    vet = new Vet(6, "Sharon", "Jenkins");
    vetRepository.save(vet);
  }

  private void preloadSpecialties() {
    Specialty specialty;
    specialty = new Specialty(1, "radiology");
    specialtyRepository.save(specialty);
    specialty = new Specialty(2, "surgery");
    specialtyRepository.save(specialty);
    specialty = new Specialty(3, "dentistry");
    specialtyRepository.save(specialty);
  }

}
