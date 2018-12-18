package org.springframework.samples.petclinic.user;

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
public class UserResourceTests extends AbstractRestControllerTest {

  static Properties properties;

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
    User user = new User(999, "firtsName", "lastName"); // , "String address", "city", "6131112222");
    String content = toJson(user);
    String expectedResponse = "{  \"firstName\" : \"firtsName\",  \"lastName\" : \"lastName\",  \"_links\" : {    \"self\" : {      \"href\" : \"http://localhost:8080/users/6\"    },    \"user\" : {      \"href\" : \"http://localhost:8080/users/6\"    }  }}";
    this.post(path, HttpStatus.CREATED, content, expectedResponse, properties);
  }

  // @GetMapping("/users/{userId}")
  @Test
  public void users_userId_shouldGetAUserInJSonFormat() throws Exception {
    String path = getPath("/users/1");
    String expectedResponse = "{  \"firstName\" : \"Michel\",  \"lastName\" : \"Carter\",  \"_links\" : {    \"self\" : {      \"href\" : \"http://localhost:8080/users/1\"    },    \"user\" : {      \"href\" : \"http://localhost:8080/users/1\"    }  }}";
    this.get(path, HttpStatus.OK, expectedResponse, properties);
  }

  // @GetMapping("/users/list")
  @Test
  public void users_list_shouldGetAListOfUsersInJSonFormat() throws Exception {
    String path = getPath("/users");
    String expectedResponse = null; // because of post can't in no order can't check
    this.get(path, HttpStatus.OK, expectedResponse, properties);
  }

  // @PutMapping("/users/{userId}")
  @Test
  public void users_userId_shouldPutAUsersInJSonFormat() throws Exception {
    String path = getPath("/users/3");
    User user = new User(3, "firstName", "lastName"); // , "String address", "city", "6131112222");
    String content = toJson(user);
    String expectedResponse = "{  \"firstName\" : \"firstName\",  \"lastName\" : \"lastName\",  \"_links\" : {    \"self\" : {      \"href\" : \"http://localhost:8080/users/3\"    },    \"user\" : {      \"href\" : \"http://localhost:8080/users/3\"    }  }}";
    this.put(path, HttpStatus.OK, content, expectedResponse, properties);
  }

}