package org.springframework.samples.petclinic.owner;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.test.AbstractRestControllerTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureTestEntityManager
public class PetResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // @GetMapping("/petsTypes")
  @Test
  public void petTypes_shouldGetAListOfPetTypesInJSonFormat() throws Exception {
    String path = getPath("/petTypes");
    String expectedResponse = "[{\"id\":1,\"name\":\"cat\"},{\"id\":2,\"name\":\"dog\"},{\"id\":3,\"name\":\"lizard\"},{\"id\":4,\"name\":\"snake\"},{\"id\":5,\"name\":\"bird\"},{\"id\":6,\"name\":\"hamster\"}]";
    this.get(path, HttpStatus.OK, expectedResponse, properties);
  }

  // @PostMapping("/owners/{ownerId}/pets")
  @Test
  public void owners_ownerId_pets_shouldGetStatusNoContent() throws Exception {
    String path = getPath("/owners/1/pets");
    String content = toJson(setupPet());
    String expectedResponse = "";
    this.post(path, HttpStatus.NO_CONTENT, content, expectedResponse, properties);
  }

  // @PutMapping("/owners/{ownerId}/pets/{petId}")
  @Test
  public void owners_ownerId_pets_petId_shouldGetAPetInJSonFormat() throws Exception {
    String path = getPath("/owners/1/pets/1");
    PetResource.PetRequest petRequest = new PetResource.PetRequest();
    petRequest.setId(1);
    petRequest.setName("some name");
    petRequest.setTypeId(1);
    String content = toJson(petRequest);
    String expectedResponse = "";
    this.put(path, HttpStatus.NO_CONTENT, content, expectedResponse, properties);
  }

  // @GetMapping("/owners/*/pets/{petId}")
  @Test
  public void owners_any_pets_petId_shouldGetAListOfPetTypesInJSonFormat() throws Exception {
    String path = getPath("/owners/*/pets/1");
    String expectedResponse = "{\"id\":1,\"name\":\"Leo\",\"owner\":\"George Franklin\",\"birthDate\":\"2010-09-07\",\"type\":{\"id\":1,\"name\":\"cat\"}}";
    this.get(path, HttpStatus.OK, expectedResponse, properties);
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
