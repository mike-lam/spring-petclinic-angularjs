package org.springframework.samples.petclinic.visit;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureTestEntityManager
public class VisitResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // @PostMapping("/owners/{ownerId}/pets/{petId}/visits")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetStatusCreated() throws Exception {
    String path = getPath("/owners/1/pets/1/visits");
    Date d = new Date();
    Visit visit = new Visit(1, 2, d, "String description");
    String content = toJsonString(visit);
    String expectedResponse = "";
    this.post(path, HttpStatus.NO_CONTENT, content, expectedResponse, properties);
  }

  // @GetMapping("/owners/{ownerId}/pets/{petId}/visits")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetAOwnerInJSonFormat() throws Exception {
    String path = getPath("/owners/1/pets/1/visits");
    String expectedResponse = "[{\"id\":1,\"date\":\"2013-01-01\",\"description\":\"rabies shot\",\"petId\":1}]";
    this.get(path, HttpStatus.OK, expectedResponse, properties);
  }

}
