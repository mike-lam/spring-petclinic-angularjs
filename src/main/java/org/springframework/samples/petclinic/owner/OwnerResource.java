package org.springframework.samples.petclinic.owner;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class OwnerResource {

  @Autowired
  private OwnerRepository ownerRepository;

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  private Owner retrieveOwner(int ownerId) {
    return this.ownerRepository.findById(ownerId).get();
  }

  /**
   * Create Owner
   */
  @RequestMapping(value = "/owners", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public void createOwner(@Valid @RequestBody Owner owner) {
    this.ownerRepository.save(owner);
  }

  /**
   * Read single Owner
   */
  @RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.GET)
  public Owner findOwner(@PathVariable("ownerId") int ownerId) {
    return retrieveOwner(ownerId);
  }

  /**
   * Read List of Owners
   */
  @GetMapping("/owners/list")
  public Collection<Owner> findAll() {
    return ownerRepository.findAll();
  }

  /**
   * Update Owner
   */
  @RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.PUT)
  public Owner updateOwner(@PathVariable("ownerId") int ownerId, @Valid @RequestBody Owner ownerRequest) {
    Owner ownerModel = retrieveOwner(ownerId);
    // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
    ownerModel.setFirstName(ownerRequest.getFirstName());
    ownerModel.setLastName(ownerRequest.getLastName());
    ownerModel.setCity(ownerRequest.getCity());
    ownerModel.setAddress(ownerRequest.getAddress());
    ownerModel.setTelephone(ownerRequest.getTelephone());
    this.ownerRepository.save(ownerModel);
    return ownerModel;
  }

}
