package org.springframework.samples.petclinic.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRestControllerTest {

  protected static final String PATH = "http://localhost:8080";

  @Autowired
  protected ObjectMapper objectMapper;

  ///////////////////////////
  protected JsonNode get(String path, HttpStatus expectedStatus, Properties properties) throws Exception {
    HttpURLConnection connection = getConnection(path);
    setProperties(properties, connection);
    connection.setRequestMethod("GET");
    assertEquals(expectedStatus.value(), connection.getResponseCode());
    String response = getResponseBody(connection);
    return toJsonNode(response);
  }

  protected JsonNode delete(String path, HttpStatus expectedStatus, Properties properties) throws Exception {
    HttpURLConnection connection = getConnection(path);
    setProperties(properties, connection);
    connection.setRequestMethod("DELETE");
    assertEquals(expectedStatus.value(), connection.getResponseCode());
    String response = getResponseBody(connection);
    return toJsonNode(response);
  }

  protected JsonNode post(String path, HttpStatus expectedStatus, String content, Properties properties)
      throws Exception {
    HttpURLConnection connection = getConnection(path);
    setProperties(properties, connection);
    connection.setRequestMethod("POST");
    setRequestBody(content, connection);
    assertEquals(expectedStatus.value(), connection.getResponseCode());
    String response = getResponseBody(connection);
    return toJsonNode(response);
  }

  protected JsonNode put(String path, HttpStatus expectedStatus, String content, Properties properties)
      throws Exception {
    HttpURLConnection connection = getConnection(path);
    setProperties(properties, connection);
    connection.setRequestMethod("PUT");
    setRequestBody(content, connection);
    assertEquals(expectedStatus.value(), connection.getResponseCode());
    String response = getResponseBody(connection);
    return toJsonNode(response);
  }

  /////////////////

  protected void get(String path, HttpStatus expectedStatus, String expectedResponse, Properties properties)
      throws Exception {
    HttpURLConnection connection = getConnection(path);
    setProperties(properties, connection);
    connection.setRequestMethod("GET");
    assertEquals(expectedStatus.value(), connection.getResponseCode());
    if (expectedResponse != null) {
      String response = getResponseBody(connection);
      assertEquals(expectedResponse, response);
    }
  }

  protected void post(String path, HttpStatus expectedStatus, String content, String expectedResponse,
      Properties properties) throws Exception {
    HttpURLConnection connection = getConnection(path);
    setProperties(properties, connection);
    connection.setRequestMethod("POST");
    setRequestBody(content, connection);
    assertEquals(expectedStatus.value(), connection.getResponseCode());
    if (expectedResponse != null) {
      String response = getResponseBody(connection);
      assertEquals(expectedResponse, response);
    }
  }

  protected void put(String path, HttpStatus expectedStatus, String content, String expectedResponse,
      Properties properties) throws Exception {
    HttpURLConnection connection = getConnection(path);
    setProperties(properties, connection);
    connection.setRequestMethod("PUT");
    setRequestBody(content, connection);
    assertEquals(expectedStatus.value(), connection.getResponseCode());
    if (expectedResponse != null) {
      String response = getResponseBody(connection);
      assertEquals(expectedResponse, response);
    }
  }

  /////////

  protected String getPath(String suffix) {
    return PATH + suffix;
  }

  // FIXME change this to toJsonString
  protected String toJsonString(Object obj) throws Exception {
    return objectMapper.writeValueAsString(obj);
  }

  protected JsonNode toJsonNode(String jsonStr) throws IOException {
    JsonNode jsonNode = objectMapper.readTree(jsonStr);
    // sample use String color = jsonNode.get("color").asText();
    return jsonNode;
  }

  protected HttpURLConnection getConnection(String path) throws Exception {
    URL url = new URL(path);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setRequestProperty("Content-Type", "application/json"); // TODO should this be like that?
    connection.setRequestProperty("Accept", "application/json");
    return connection;
  }

  protected void setRequestBody(String in, HttpURLConnection connection) throws Exception {
    PrintWriter out = new PrintWriter(connection.getOutputStream());
    out.print(in);
    out.close();
  }

  protected String getResponseBody(HttpURLConnection connection) throws Exception {
    StringBuilder ret = new StringBuilder();
    BufferedReader inBuff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String line;
    while ((line = inBuff.readLine()) != null) {
      ret.append(line);
    }
    inBuff.close();
    return ret.toString();
  }

  private void setProperties(Properties properties, HttpURLConnection connection) {
    if (properties != null) {
      for (Iterator iterator = properties.keySet().iterator(); iterator.hasNext();) {
        String key = (String) iterator.next();
        String value = properties.getProperty(key);
        connection.setRequestProperty(key, value);
      }
    }
  }

  // ////////// FIXME delete some below //////////////////////////////////////////////////////
  // protected void putNotFound(String in, String path) throws Exception {
  // HttpURLConnection connection = getConnection(path);
  // connection.setRequestMethod("PUT");
  // setRequestBody(in, connection);
  // assertEquals(HttpStatus.NOT_FOUND.value(), connection.getResponseCode());
  // }
  //
  // protected void delete(String path) throws Exception {
  // HttpURLConnection connection = getConnection(path);
  // connection.setRequestMethod("DELETE");
  // assertEquals(HttpStatus.OK.value(), connection.getResponseCode());
  // }
  //
  // protected void put(String in, String path, String expected) throws Exception {
  // HttpURLConnection connection = getConnection(path);
  // connection.setRequestMethod("PUT");
  // setRequestBody(in, connection);
  // assertEquals(HttpStatus.OK.value(), connection.getResponseCode());
  // String response = getResponseBody(connection);
  // assertEquals(expected, response);
  // }
  //
  // protected void post(String in, String path, String expected) throws Exception {
  // HttpURLConnection connection = getConnection(path);
  // connection.setRequestMethod("POST");
  // setRequestBody(in, connection);
  // assertEquals(HttpStatus.OK.value(), connection.getResponseCode());
  // String response = getResponseBody(connection);
  // assertEquals(expected, response);
  // }
  //
  // protected void postValidationError(String in, String path, String errMsg) throws Exception {
  // HttpURLConnection connection = getConnection(path);
  // connection.setRequestMethod("POST");
  // setRequestBody(in, connection);
  // connection.getResponseCode();
  // assertEquals(HttpStatus.BAD_REQUEST.value(), connection.getResponseCode());
  // assertTrue(getErrorStream(connection).contains(errMsg));
  // }
  //
  // protected String getErrorStream(HttpURLConnection connection) throws Exception {
  // StringBuilder ret = new StringBuilder();
  // BufferedReader inBuff = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
  // String line;
  // while ((line = inBuff.readLine()) != null) {
  // ret.append(line);
  // }
  // inBuff.close();
  // return ret.toString();
  // }

}