package org.springframework.samples.petclinic.vet;

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
    String expectedResponse = "[{\"id\":1,\"firstName\":\"James\",\"lastName\":\"Carter\",\"specialties\":[],\"nrOfSpecialties\":0},{\"id\":2,\"firstName\":\"Helen\",\"lastName\":\"Leary\",\"specialties\":[{\"id\":1,\"name\":\"radiology\"}],\"nrOfSpecialties\":1},{\"id\":3,\"firstName\":\"Linda\",\"lastName\":\"Douglas\",\"specialties\":[{\"id\":3,\"name\":\"dentistry\"},{\"id\":2,\"name\":\"surgery\"}],\"nrOfSpecialties\":2},{\"id\":4,\"firstName\":\"Rafael\",\"lastName\":\"Ortega\",\"specialties\":[{\"id\":2,\"name\":\"surgery\"}],\"nrOfSpecialties\":1},{\"id\":5,\"firstName\":\"Henry\",\"lastName\":\"Stevens\",\"specialties\":[],\"nrOfSpecialties\":0},{\"id\":6,\"firstName\":\"Sharon\",\"lastName\":\"Jenkins\",\"specialties\":[],\"nrOfSpecialties\":0}]";
    this.get(path, HttpStatus.OK, expectedResponse, properties);
  }

}
