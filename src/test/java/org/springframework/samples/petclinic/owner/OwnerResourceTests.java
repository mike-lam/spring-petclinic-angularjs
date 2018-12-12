package org.springframework.samples.petclinic.owner;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(OwnerResource.class)
public class OwnerResourceTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private OwnerRepository ownerRepository;

  // @RequestMapping(value = "/owners", method = RequestMethod.POST)
  @Test
  public void owners_shouldGetStatusCreated() throws Exception {
    Owner owner = new Owner(1, "firtsName", "lastName", "String address", "city", "6131112222");
    String json = new ObjectMapper().writeValueAsString(owner);
    mvc.perform(post("/owners").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
  }

  // @RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.GET)
  @Test
  public void owners_ownerId_shouldGetAOwnerInJSonFormat() throws Exception {
    Owner owner = new Owner(1, "firstName", "lastName", "String address", "city", "6131112222");

    given(ownerRepository.findById(1)).willReturn(Optional.of(owner));

    mvc.perform(get("/owners/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.firstName").value("firstName")).andExpect(jsonPath("$.lastName").value("lastName"))
        .andExpect(jsonPath("$.address").value("String address")).andExpect(jsonPath("$.city").value("city"))
        .andExpect(jsonPath("$.telephone").value("6131112222"));
  }

  // @GetMapping("/owners/list")
  @Test
  public void owners_list_shouldGetAListOfOwnersInJSonFormat() throws Exception {
    Owner owner = new Owner(1, "firstName", "lastName", "String address", "city", "6131112222");

    given(ownerRepository.findAll()).willReturn(Arrays.asList(owner));

    mvc.perform(get("/owners/list").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].firstName").value("firstName")).andExpect(jsonPath("$[0].lastName").value("lastName"))
        .andExpect(jsonPath("$[0].address").value("String address")).andExpect(jsonPath("$[0].city").value("city"))
        .andExpect(jsonPath("$[0].telephone").value("6131112222"));
  }

  // @RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.PUT)
  @Test
  public void owners_ownerId_shouldPutAOwnersInJSonFormat() throws Exception {
    Owner owner = new Owner(1, "firstName", "lastName", "String address", "city", "6131112222");

    given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
    String json = new ObjectMapper().writeValueAsString(owner);

    mvc.perform(put("/owners/1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.firstName").value("firstName"))
        .andExpect(jsonPath("$.lastName").value("lastName")).andExpect(jsonPath("$.address").value("String address"))
        .andExpect(jsonPath("$.city").value("city")).andExpect(jsonPath("$.telephone").value("6131112222"));
  }

}
