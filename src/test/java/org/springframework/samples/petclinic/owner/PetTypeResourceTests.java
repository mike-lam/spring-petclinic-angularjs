package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class PetTypeResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  static int listCountadjust = 0;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // @PostMapping("/petTypes")
  @Test
  public void petTypes_shouldGetStatusCreated() throws Exception {
    String path = getPath("/petTypes");
    PetType petType = new PetType(7, "XXXX");
    String content = toJsonString(petType);
    JsonNode response = this.post(path, HttpStatus.CREATED, content, properties);
    listCountadjust++;
    assertEquals("XXXX", response.get("name").asText());
    assertEquals("http://localhost:8080/petTypes/7", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/petTypes/7", response.get("_links").get("petType").get("href").asText());
  }

  // @GetMapping("/petTypes/{petId}")
  @Test
  public void pets_petId_shouldGetAPetInJSonFormat() throws Exception {
    String path = getPath("/petTypes/1");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertEquals("cat", response.get("name").asText());
    assertEquals("http://localhost:8080/petTypes/1", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/petTypes/1", response.get("_links").get("petType").get("href").asText());
  }

  // @GetMapping("/petTypes")
  @Test
  public void pets_shouldGetAListOfOwnersInJSonFormat() throws Exception {
    String path = getPath("/petTypes");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertTrue(response.get("_embedded").get("petTypes").isArray()); // cant control order of list
    assertEquals(20, response.get("page").get("size").asInt());
    assertEquals(6 + listCountadjust, response.get("page").get("totalElements").asInt());
    assertEquals(1, response.get("page").get("totalPages").asInt());
    assertEquals(0, response.get("page").get("number").asInt());
  }

  // @PutMapping("/petTypes/{petId}")
  @Test
  public void pets_ownerId_shouldPutAOwnersInJSonFormat() throws Exception {
    String path = getPath("/petTypes/3");
    PetType petType = new PetType(3, "UPDATE");
    String content = toJsonString(petType);
    JsonNode response = this.put(path, HttpStatus.OK, content, properties);
    assertEquals("UPDATE", response.get("name").asText());
    assertEquals("http://localhost:8080/petTypes/3", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/petTypes/3", response.get("_links").get("petType").get("href").asText());
  }

}
