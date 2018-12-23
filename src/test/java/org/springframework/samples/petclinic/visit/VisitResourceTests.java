package org.springframework.samples.petclinic.visit;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.test.AbstractRestControllerTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureTestEntityManager
public class VisitResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  @Autowired
  private PetRepository petRepository;

  static int listCountadjust = 0;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // @PostMapping("/visits")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetStatusCreated() throws Exception {
    String path = getPath("/visits");
    Date d = new Date();
    Visit visit = new Visit(5, 2, d, "String description");
    String content = toJsonString(visit);
    JsonNode response = this.post(path, HttpStatus.CREATED, content, properties);
    listCountadjust++;
    assertEquals("String description", response.get("description").asText());
    assertEquals("2", response.get("petId").asText());
    assertEquals("http://localhost:8080/visits/5", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/visits/5", response.get("_links").get("visit").get("href").asText());
  }

  // @GetMapping("/visits/search/pet?petId={petId}")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetAOwnerInJSonFormat() throws Exception {
    String path = getPath("/visits/search/pet?petId=1");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertEquals("2013-01-01", response.get("_embedded").get("visits").get(0).get("date").asText());
    assertEquals("rabies shot", response.get("_embedded").get("visits").get(0).get("description").asText());
    assertEquals("1", response.get("_embedded").get("visits").get(0).get("petId").asText());
    assertEquals("http://localhost:8080/visits/search/pet?petId=1",
        response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/visits/1",
        response.get("_embedded").get("visits").get(0).get("_links").get("self").get("href").asText());
  }

}
