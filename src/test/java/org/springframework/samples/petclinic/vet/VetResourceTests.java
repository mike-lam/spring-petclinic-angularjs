package org.springframework.samples.petclinic.vet;

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
public class VetResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // @GetMapping("/vets")
  @Test
  public void vets_shouldGetAListOfVetsInJSonFormat() throws Exception {
    String path = getPath("/vets");
    this.get(path, HttpStatus.OK, properties);
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertTrue(response.get("_embedded").get("vets").isArray()); // cant control order of list
    assertEquals(20, response.get("page").get("size").asInt());
    assertEquals(6, response.get("page").get("totalElements").asInt());
    assertEquals(1, response.get("page").get("totalPages").asInt());
    assertEquals(0, response.get("page").get("number").asInt());
  }

}
