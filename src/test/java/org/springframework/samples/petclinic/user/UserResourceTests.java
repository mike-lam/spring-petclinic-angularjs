package org.springframework.samples.petclinic.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
public class UserResourceTests extends AbstractRestControllerTest {

  static Properties properties;

  static int listCountadjust = 0;

  @BeforeClass
  static public void BeforeClass() {
    properties = new Properties();
    properties.setProperty("content-type", MediaType.APPLICATION_JSON.toString());
    properties.setProperty("Accept", MediaType.APPLICATION_JSON.toString());
  }

  // @PostMapping("/users")
  @Test
  public void users_shouldGetStatusCreated() throws Exception {
    String path = getPath("/users");
    User user = new User(999, "firstName", "lastName", "guest"); // , "String address", "city", "6131112222");
    String content = toJsonString(user);
    JsonNode response = this.post(path, HttpStatus.CREATED, content, properties);
    listCountadjust++;
    assertEquals("firstName", response.get("firstName").asText());
    assertEquals("lastName", response.get("lastName").asText());
    assertEquals("guest", response.get("role").asText());
    assertEquals("http://localhost:8080/users/6", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/users/6", response.get("_links").get("user").get("href").asText());
  }

  // @GetMapping("/users/{userId}")
  @Test
  public void users_userId_shouldGetAUserInJSonFormat() throws Exception {
    String path = getPath("/users/1");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertEquals(1, response.get("id").asInt());
    assertEquals("Michel", response.get("firstName").asText());
    assertEquals("Carter", response.get("lastName").asText());
    assertEquals("papa", response.get("role").asText());
    assertEquals("http://localhost:8080/users/1", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/users/1", response.get("_links").get("user").get("href").asText());
  }

  // @GetMapping("/users")
  @Test
  public void users_list_shouldGetAListOfUsersInJSonFormat() throws Exception {
    String path = getPath("/users");
    JsonNode response = this.get(path, HttpStatus.OK, properties);
    assertTrue(response.get("_embedded").get("users").isArray()); // cant control order of list
    assertEquals(20, response.get("page").get("size").asInt());
    assertEquals(5 + listCountadjust, response.get("page").get("totalElements").asInt());
    assertEquals(1, response.get("page").get("totalPages").asInt());
    assertEquals(0, response.get("page").get("number").asInt());
  }

  // @PutMapping("/users/{userId}")
  @Test
  public void users_userId_shouldPutAUsersInJSonFormat() throws Exception {
    String path = getPath("/users/3");
    User user = new User(3, "firstName", "lastName", "guest"); // , "String address", "city", "6131112222");
    String content = toJsonString(user);
    JsonNode response = this.put(path, HttpStatus.OK, content, properties);
    assertEquals("firstName", response.get("firstName").asText());
    assertEquals("lastName", response.get("lastName").asText());
    assertEquals("guest", response.get("role").asText());
    assertEquals("http://localhost:8080/users/3", response.get("_links").get("self").get("href").asText());
    assertEquals("http://localhost:8080/users/3", response.get("_links").get("user").get("href").asText());
  }

  // @DeleteMapping("/users/{userId}")
  @Test
  public void users_userId_shouldDeleteAUsersInJSonFormat() throws Exception {
    String path = getPath("/users/2");
    JsonNode response = this.delete(path, HttpStatus.NO_CONTENT, properties);
    listCountadjust--;
    assertNull(response);
  }

}