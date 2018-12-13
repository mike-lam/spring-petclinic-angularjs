package org.springframework.samples.petclinic.owner;

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
public class OwnerResourceTests extends AbstractRestControllerTest {

  // @PostMapping("/owners")
  @Test
  public void owners_shouldGetStatusCreated() throws Exception {
    String path = getPath("/owners");
    Owner owner = new Owner(999, "firtsName", "lastName", "String address", "city", "6131112222");
    String content = toJson(owner);
    String expectedResponse = "";
    this.post(path, HttpStatus.CREATED, content, expectedResponse);
    // mvc.perform(post("/owners").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
  }

  // @GetMapping("/owners/{ownerId}")
  @Test
  public void owners_ownerId_shouldGetAOwnerInJSonFormat() throws Exception {
    String path = getPath("/owners/1");
    String expectedResponse = "{\"id\":1,\"firstName\":\"George\",\"lastName\":\"Franklin\",\"address\":\"110 W. Liberty St.\",\"city\":\"Madison\",\"telephone\":\"6085551023\",\"pets\":[{\"id\":1,\"name\":\"Leo\",\"birthDate\":\"2010-09-07\",\"type\":{\"id\":1,\"name\":\"cat\"}}]}";
    this.get(path, HttpStatus.OK, expectedResponse);
    // mvc.perform(get("/owners/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
    // .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$.id").value(1))
    // .andExpect(jsonPath("$.firstName").value("firstName")).andExpect(jsonPath("$.lastName").value("lastName"))
    // .andExpect(jsonPath("$.address").value("String address")).andExpect(jsonPath("$.city").value("city"))
    // .andExpect(jsonPath("$.telephone").value("6131112222"));
  }

  // @GetMapping("/owners/list")
  @Test
  public void owners_list_shouldGetAListOfOwnersInJSonFormat() throws Exception {
    String path = getPath("/owners/list");
    String expectedResponse = null; // because of post can't in no order can't check
    this.get(path, HttpStatus.OK, expectedResponse);
    // mvc.perform(get("/owners/list").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
    // .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$[0].id").value(1))
    // .andExpect(jsonPath("$[0].firstName").value("firstName")).andExpect(jsonPath("$[0].lastName").value("lastName"))
    // .andExpect(jsonPath("$[0].address").value("String address")).andExpect(jsonPath("$[0].city").value("city"))
    // .andExpect(jsonPath("$[0].telephone").value("6131112222"));
  }

  // @PutMapping("/owners/{ownerId}")
  @Test
  public void owners_ownerId_shouldPutAOwnersInJSonFormat() throws Exception {
    String path = getPath("/owners/3");
    Owner owner = new Owner(3, "firstName", "lastName", "String address", "city", "6131112222");
    String content = toJson(owner);
    String expectedResponse = "{\"id\":3,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"address\":\"String address\",\"city\":\"city\",\"telephone\":\"6131112222\",\"pets\":[{\"id\":4,\"name\":\"Jewel\",\"birthDate\":\"2010-03-07\",\"type\":{\"id\":2,\"name\":\"dog\"}},{\"id\":3,\"name\":\"Rosy\",\"birthDate\":\"2011-04-17\",\"type\":{\"id\":2,\"name\":\"dog\"}}]}";
    this.put(path, HttpStatus.OK, content, expectedResponse);
    // mvc.perform(put("/owners/1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk())
    // .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.firstName").value("firstName"))
    // .andExpect(jsonPath("$.lastName").value("lastName")).andExpect(jsonPath("$.address").value("String address"))
    // .andExpect(jsonPath("$.city").value("city")).andExpect(jsonPath("$.telephone").value("6131112222"));
  }

}
