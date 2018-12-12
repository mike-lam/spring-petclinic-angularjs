package org.springframework.samples.petclinic.visit;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

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
@WebMvcTest(VisitResource.class)
public class VisitResourceTests {

  @Autowired
  private MockMvc mvc;

  // @MockBean
  // private PetRepository petRepository;
  // @MockBean
  // private PetTypeRepository petTypeRepository;
  @MockBean
  private VisitRepository visitRepository;

  // @PostMapping("/owners/{ownerId}/pets/{petId}/visits")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetStatusCreated() throws Exception {
    Visit visit = new Visit(1, 2, new Date(), "String description");
    String json = new ObjectMapper().writeValueAsString(visit);
    mvc.perform(post("/owners/1/pets/2/visits").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isNoContent());
  }

  // @GetMapping("/owners/{ownerId}/pets/{petId}/visits")
  @Test
  public void owners_ownerId_pets_petId_visits_shouldGetAOwnerInJSonFormat() throws Exception {
    Date d = new Date();
    Visit visit = new Visit(1, 2, d, "String description");
    given(visitRepository.findByPetId(2)).willReturn(Arrays.asList(visit));

    mvc.perform(get("/owners/1/pets/2/visits").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].petId").value(2)).andExpect(jsonPath("$[0].description").value("String description"));
  }

}
