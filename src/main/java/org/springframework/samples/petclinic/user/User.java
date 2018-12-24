package org.springframework.samples.petclinic.user;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.Person;

@Entity
public class User extends Person {

  @NotEmpty
  protected String Role;

  public User() {
  }

  public User(int i, String firstName, String lastName, String role) {
    this.setId(i);
    this.setFirstName(firstName);
    this.setLastName(lastName);
    this.setRole(role);
  }

  public String getRole() {
    return Role;
  }

  public void setRole(String role) {
    Role = role;
  }

}
