package org.springframework.samples.petclinic.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

@Configuration
public class InitDatasourceConfig {

  @Autowired
  private PetRepository petRepository;
  @Autowired
  private PetTypeRepository petTypeRepository;
  @Autowired
  private VetRepository vetRepository;
  @Autowired
  private OwnerRepository ownerRepository;
  @Autowired
  private VisitRepository visitRepository;
  @Autowired
  private SpecialtyRepository specialtyRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    preloadOwners();
    preloadPetTypes();
    preloadPets();
    preloadVisits();
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

  private void preloadOwners() {
    Owner owner;
    owner = new Owner(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
    ownerRepository.save(owner);
    owner = new Owner(2, "Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749");
    ownerRepository.save(owner);
    owner = new Owner(3, "Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763");
    ownerRepository.save(owner);
    owner = new Owner(4, "Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198");
    ownerRepository.save(owner);
    owner = new Owner(5, "Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765");
    ownerRepository.save(owner);
    owner = new Owner(6, "Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654");
    ownerRepository.save(owner);
    owner = new Owner(7, "Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387");
    ownerRepository.save(owner);
    owner = new Owner(8, "Maria", "Escobito", "345 Maple St.", "Madison", "6085557683");
    ownerRepository.save(owner);
    owner = new Owner(9, "David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435");
    ownerRepository.save(owner);
    owner = new Owner(10, "Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487");
    ownerRepository.save(owner);
  }

  private void preloadPetTypes() {
    PetType petType;
    petType = new PetType(1, "cat");
    petTypeRepository.save(petType);
    petType = new PetType(2, "dog");
    petTypeRepository.save(petType);
    petType = new PetType(3, "lizard");
    petTypeRepository.save(petType);
    petType = new PetType(4, "snake");
    petTypeRepository.save(petType);
    petType = new PetType(5, "bird");
    petTypeRepository.save(petType);
    petType = new PetType(6, "hamster");
    petTypeRepository.save(petType);
  }

  private void preloadPets() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Pet pet;
    try {
      pet = new Pet(1, "Leo", df.parse("2010-09-07"), petTypeRepository.findById(1).get(),
          ownerRepository.findById(1).get());
      petRepository.save(pet);
      pet = new Pet(2, "Basil", df.parse("2012-08-06"), petTypeRepository.findById(6).get(),
          ownerRepository.findById(2).get());
      petRepository.save(pet);
      pet = new Pet(3, "Rosy", df.parse("2011-04-17"), petTypeRepository.findById(2).get(),
          ownerRepository.findById(3).get());
      petRepository.save(pet);
      pet = new Pet(4, "Jewel", df.parse("2010-03-07"), petTypeRepository.findById(2).get(),
          ownerRepository.findById(3).get());
      petRepository.save(pet);
      pet = new Pet(5, "Iggy", df.parse("2010-11-30"), petTypeRepository.findById(3).get(),
          ownerRepository.findById(4).get());
      petRepository.save(pet);
      pet = new Pet(6, "George", df.parse("2010-01-20"), petTypeRepository.findById(4).get(),
          ownerRepository.findById(5).get());
      petRepository.save(pet);
      pet = new Pet(7, "Samantha", df.parse("2012-09-04"), petTypeRepository.findById(1).get(),
          ownerRepository.findById(6).get());
      petRepository.save(pet);
      pet = new Pet(8, "Max", df.parse("2012-09-04"), petTypeRepository.findById(1).get(),
          ownerRepository.findById(6).get());
      petRepository.save(pet);
      pet = new Pet(9, "Lucky", df.parse("2011-08-06"), petTypeRepository.findById(5).get(),
          ownerRepository.findById(7).get());
      petRepository.save(pet);
      pet = new Pet(10, "Mulligan", df.parse("2007-02-24"), petTypeRepository.findById(2).get(),
          ownerRepository.findById(8).get());
      petRepository.save(pet);
      pet = new Pet(11, "Freddy", df.parse("2010-03-09"), petTypeRepository.findById(5).get(),
          ownerRepository.findById(9).get());
      petRepository.save(pet);
      pet = new Pet(12, "Lucky", df.parse("2010-06-24"), petTypeRepository.findById(2).get(),
          ownerRepository.findById(10).get());
      petRepository.save(pet);
      pet = new Pet(13, "Sly", df.parse("2012-06-08"), petTypeRepository.findById(1).get(),
          ownerRepository.findById(10).get());
      petRepository.save(pet);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private void preloadVisits() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Visit visit;
    try {
      visit = new Visit(1, petRepository.findById(7), df.parse("2013-01-01"), "rabies shot");
      visitRepository.save(visit);
      visit = new Visit(2, petRepository.findById(8), df.parse("2013-01-02"), "rabies shot");
      visitRepository.save(visit);
      visit = new Visit(3, petRepository.findById(8), df.parse("2013-01-03"), "neutered");
      visitRepository.save(visit);
      visit = new Visit(4, petRepository.findById(7), df.parse("2013-01-04"), "spayed");
      visitRepository.save(visit);
    } catch (ParseException e) {
      e.printStackTrace();
    }
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
