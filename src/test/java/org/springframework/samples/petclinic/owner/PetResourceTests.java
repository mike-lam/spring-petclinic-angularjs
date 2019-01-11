package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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

import com.fasterxml.jackson.databind.JsonNode;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureTestEntityManager
public class PetResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  static int listCountadjust = 0;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // // @PostMapping("/pets")
  @Test
  public void pets_shouldGetStatusCreated() throws Exception {
    String path = getPath("/pets");
    Date d = new Date();
    Pet pet = new Pet(14, "XXXX", d, new PetType(1, "cat"),
        new Owner(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023"));
    Owner tmp = new Owner();
    tmp.setId(1);
    pet.setOwner(tmp);
    String content = toJsonString(pet);
    JsonNode response = this.post(path, HttpStatus.CREATED, content, properties);
    listCountadjust++;
    assertEquals("XXXX", response.get("name").asText());
    // not same date format assertEquals(d.toString(), response.get("birthDate").asText());
    assertEquals("http://localhost:8080/pets/14/type", response.get("_links").get("type").get("href").asText());
    assertEquals("http://localhost:8080/pets/14/owner", response.get("_links").get("owner").get("href").asText());
    assertEquals("http://localhost:8080/pets/14", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/pets/14", response.get("_links").get("pet").get("href").asText());
    // response = this.get(path + "/14", HttpStatus.OK, properties);
    // System.out.println(response);
    // response = this.get(getPath("/owners") + "/1", HttpStatus.OK, properties);
    // System.out.println(response);
    // response = this.get(getPath("/pets/14/owner"), HttpStatus.OK, properties);
    // System.out.println(response);
    //
  }

  // @GetMapping("/pets/{petId}")
  @Test
  public void pets_petId_shouldGetAPetInJSonFormat() throws Exception {
    String path = getPath("/pets/1");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertEquals("Leo", response.get("name").asText());
    assertEquals("2010-09-07", response.get("birthDate").asText());
    assertEquals("http://localhost:8080/pets/1/type", response.get("_links").get("type").get("href").asText());
    assertEquals("http://localhost:8080/pets/1/owner", response.get("_links").get("owner").get("href").asText());
    assertEquals("http://localhost:8080/pets/1", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/pets/1", response.get("_links").get("pet").get("href").asText());
  }

  // @GetMapping("/pets")
  @Test
  public void pets_shouldGetAListOfOwnersInJSonFormat() throws Exception {
    String path = getPath("/pets");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertTrue(response.get("_embedded").get("pets").isArray()); // cant control order of list
    assertEquals(20, response.get("page").get("size").asInt());
    assertEquals(13 + listCountadjust, response.get("page").get("totalElements").asInt());
    assertEquals(1, response.get("page").get("totalPages").asInt());
    assertEquals(0, response.get("page").get("number").asInt());
  }

  // FIXME
  // // @PutMapping("/pets/{ownerId}")
  // @Test
  // public void pets_ownerId_shouldPutAOwnersInJSonFormat() throws Exception {
  // String path = getPath("/pets/3");
  // Owner owner = new Owner(3, "firstName", "lastName", "String address", "city", "6131112222");
  // String content = toJsonString(owner);
  // JsonNode response = this.put(path, HttpStatus.OK, content, properties);
  // assertEquals("firstName", response.get("firstName").asText());
  // assertEquals("lastName", response.get("lastName").asText());
  // assertEquals("String address", response.get("address").asText());
  // assertEquals("city", response.get("city").asText());
  // assertEquals("6131112222", response.get("telephone").asText());
  // assertEquals("http://localhost:8080/pets/3/pets", response.get("_links").get("pets").get("href").asText());
  // assertEquals("http://localhost:8080/pets/3", response.get("_links").get("self").get("href").asText());
  // assertEquals("http://localhost:8080/pets/3", response.get("_links").get("owner").get("href").asText());
  // }
  //
  private Pet setupPet() {
    Owner owner = new Owner();
    owner.setId(1);
    owner.setFirstName("George");
    owner.setLastName("Bush");
    Pet pet = new Pet();
    pet.setName("Basil");
    pet.setId(2);
    pet.setBirthDate(new Date());
    PetType petType = new PetType();
    petType.setId(6);
    pet.setType(petType);
    // owner.addPet(pet);
    return pet;
  }
}
