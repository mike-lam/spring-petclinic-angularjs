package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

@CrossOrigin
@RestController
public class PetResource {

  @Autowired
  private PetTypeRepository petTypeRepository;
  @Autowired
  private PetRepository petRepository;
  @Autowired
  private OwnerRepository ownerRepository;

  @GetMapping("/petTypes")
  Collection<PetType> getPetTypes() {
    return petTypeRepository.findAll();
  }

  @PostMapping("/owners/{ownerId}/pets")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void processCreationForm(@RequestBody PetRequest petRequest, @PathVariable("ownerId") int ownerId) {
    Pet pet = new Pet();
    Owner owner = ownerRepository.findById(ownerId).get();
    owner.addPet(pet);
    save(pet, petRequest);
  }

  @PutMapping("/owners/{ownerId}/pets/{petId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void processUpdateForm(@RequestBody PetRequest petRequest) {
    save(petRepository.findById(petRequest.getId()), petRequest);
  }

  private void save(Pet pet, PetRequest petRequest) {
    pet.setName(petRequest.getName());
    pet.setBirthDate(petRequest.getBirthDate());
    for (PetType petType : petTypeRepository.findAll()) {
      if (petType.getId() == petRequest.getTypeId()) {
        pet.setType(petType);
      }
    }
    petRepository.save(pet);
  }

  @GetMapping("/owners/*/pets/{petId}")
  public PetDetails findPet(@PathVariable("petId") int petId) {
    Pet pet = this.petRepository.findById(petId);
    return new PetDetails(pet);
  }

  static class PetRequest {
    int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date birthDate;
    @Size(min = 1)
    String name;
    int typeId;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public Date getBirthDate() {
      return birthDate;
    }

    public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getTypeId() {
      return typeId;
    }

    public void setTypeId(int typeId) {
      this.typeId = typeId;
    }
  }

  static class PetDetails {

    final int id;
    final String name;
    final String owner;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    final Date birthDate;
    final PetType type;

    PetDetails(Pet pet) {
      this.id = pet.getId();
      this.name = pet.getName();
      this.owner = pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName();
      this.birthDate = pet.getBirthDate();
      this.type = pet.getType();
    }

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getOwner() {
      return owner;
    }

    public Date getBirthDate() {
      return birthDate;
    }

    public PetType getType() {
      return type;
    }
  }

}
