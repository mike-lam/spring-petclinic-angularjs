package org.springframework.samples.petclinic.owner;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PetResource.class)
public class PetResourceTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PetRepository petRepository;
  @MockBean
  private PetTypeRepository petTypeRepository;
  @MockBean
  private OwnerRepository ownerRepository;

  // @GetMapping("/petsTypes")
  @Test
  public void petTypes_shouldGetAListOfPetTypesInJSonFormat() throws Exception {
    PetType petType = new PetType(1, "aPetType");

    given(petTypeRepository.findAll()).willReturn(Arrays.asList(petType));

    mvc.perform(get("/petTypes").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("aPetType"));
  }

  // @PostMapping("/owners/{ownerId}/pets")
  @Test
  public void owners_ownerId_pets_shouldGetStatusNoContent() throws Exception {
    Pet pet = setupPet();
    Owner owner = new Owner();
    String json = new ObjectMapper().writeValueAsString(pet);
    given(ownerRepository.findById(2)).willReturn(Optional.of(owner));

    mvc.perform(post("/owners/2/pets").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isNoContent());
  }

  // @GetMapping("/owners/{ownerId}/pets/{petId}")
  @Test
  public void owners_ownerId_pets_petId_shouldGetAPetInJSonFormat() throws Exception {
    Pet pet = setupPet();

    given(petRepository.findById(2)).willReturn(pet);

    mvc.perform(get("/owners/2/pets/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("Basil")).andExpect(jsonPath("$.type.id").value(6));
  }

  // @GetMapping("/owners/*/pets/{petId}")
  @Test
  public void owners_any_pets_petId_shouldGetAListOfPetTypesInJSonFormat() throws Exception {
    Pet pet = setupPet();

    given(this.petRepository.findById(2)).willReturn(pet);

    mvc.perform(get("/owners/*/pets/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2)).andExpect(jsonPath("$.name").value("Basil"))
        .andExpect(jsonPath("$.type.id").value("6"));
  }

  private Pet setupPet() {
    Owner owner = new Owner();
    owner.setFirstName("George");
    owner.setLastName("Bush");
    Pet pet = new Pet();
    pet.setName("Basil");
    pet.setId(2);
    PetType petType = new PetType();
    petType.setId(6);
    pet.setType(petType);
    owner.addPet(pet);
    return pet;
  }

}
