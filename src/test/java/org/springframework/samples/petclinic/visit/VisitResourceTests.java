package org.springframework.samples.petclinic.visit;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.test.AbstractRestControllerTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureTestEntityManager
public class VisitResourceTests extends AbstractRestControllerTest {

  // @PostMapping("/owners/{ownerId}/pets/{petId}/visits")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetStatusCreated() throws Exception {
    String path = getPath("/owners/1/pets/1/visits");
    Date d = new Date();
    Visit visit = new Visit(1, 2, d, "String description");
    String content = toJson(visit);
    String expectedResponse = "";
    this.post(path, HttpStatus.NO_CONTENT, content, expectedResponse);
    // mvc.perform(post("/owners/1/pets/2/visits").contentType(MediaType.APPLICATION_JSON).content(json))
    // .andExpect(status().isNoContent());
  }

  // @GetMapping("/owners/{ownerId}/pets/{petId}/visits")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetAOwnerInJSonFormat() throws Exception {
    String path = getPath("/owners/1/pets/1/visits");
    String expectedResponse = "[{\"id\":1,\"date\":\"2013-01-01\",\"description\":\"rabies shot\",\"petId\":1}]";
    this.get(path, HttpStatus.OK, expectedResponse);

    // mvc.perform(get("/owners/1/pets/2/visits").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
    // .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$[0].id").value(1))
    // .andExpect(jsonPath("$[0].petId").value(2)).andExpect(jsonPath("$[0].description").value("String description"));
  }

}
