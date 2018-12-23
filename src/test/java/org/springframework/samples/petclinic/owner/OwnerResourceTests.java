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
public class OwnerResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  static int listCountadjust = 0;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // @PostMapping("/owners")
  @Test
  public void owners_shouldGetStatusCreated() throws Exception {
    String path = getPath("/owners");
    Owner owner = new Owner(999, "firstName", "lastName", "String address", "city", "6131112222");
    String content = toJsonString(owner);
    JsonNode response = this.post(path, HttpStatus.CREATED, content, properties);
    listCountadjust++;
    assertEquals("firstName", response.get("firstName").asText());
    assertEquals("lastName", response.get("lastName").asText());
    assertEquals("String address", response.get("address").asText());
    assertEquals("city", response.get("city").asText());
    assertEquals("http://localhost:8080/owners/11", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/owners/11", response.get("_links").get("owner").get("href").asText());
  }

  // @GetMapping("/owners/{ownerId}")
  @Test
  public void owners_ownerId_shouldGetAOwnerInJSonFormat() throws Exception {
    String path = getPath("/owners/1");
    // \"pets\":[{\"id\":1,\"name\":\"Leo\",\"birthDate\":\"2010-09-07\",\"type\":{\"id\":1,\"name\":\"cat\"}}]}";
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertEquals("George", response.get("firstName").asText());
    assertEquals("Franklin", response.get("lastName").asText());
    assertEquals("110 W. Liberty St.", response.get("address").asText());
    assertEquals("Madison", response.get("city").asText());
    assertEquals("6085551023", response.get("telephone").asText());
    // assertTrue(response.get("pets").isArray());
    // assertEquals("Leo", response.get("pets").get(0).get("name").asText());
    // assertEquals("2010-09-07", response.get("pets").get(0).get("birthDate").asText());
    // assertEquals("cat", response.get("pets").get(0).get("type").get("name").asText());
    assertEquals("http://localhost:8080/owners/1/pets", response.get("_links").get("pets").get("href").asText());
    assertEquals("http://localhost:8080/owners/1", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/owners/1", response.get("_links").get("owner").get("href").asText());
  }

  // @GetMapping("/owners")
  @Test
  public void owners_shouldGetAListOfOwnersInJSonFormat() throws Exception {
    String path = getPath("/owners");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertTrue(response.get("_embedded").get("owners").isArray()); // cant control order of list
    assertEquals(20, response.get("page").get("size").asInt());
    assertEquals(10 + listCountadjust, response.get("page").get("totalElements").asInt());
    assertEquals(1, response.get("page").get("totalPages").asInt());
    assertEquals(0, response.get("page").get("number").asInt());
  }

  // @PutMapping("/owners/{ownerId}")
  @Test
  public void owners_ownerId_shouldPutAOwnersInJSonFormat() throws Exception {
    String path = getPath("/owners/3");
    Owner owner = new Owner(3, "firstName", "lastName", "String address", "city", "6131112222");
    String content = toJsonString(owner);
    JsonNode response = this.put(path, HttpStatus.OK, content, properties);
    assertEquals("firstName", response.get("firstName").asText());
    assertEquals("lastName", response.get("lastName").asText());
    assertEquals("String address", response.get("address").asText());
    assertEquals("city", response.get("city").asText());
    assertEquals("6131112222", response.get("telephone").asText());
    assertEquals("http://localhost:8080/owners/3/pets", response.get("_links").get("pets").get("href").asText());
    assertEquals("http://localhost:8080/owners/3", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/owners/3", response.get("_links").get("owner").get("href").asText());
  }

}
