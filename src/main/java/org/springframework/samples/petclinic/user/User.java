package org.springframework.samples.petclinic.user;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.Person;

@Entity
public class User extends Person {

  @NotEmpty
  protected String role;

  public User() {
  }

  public User(Integer i, String firstName, String lastName, String role) {
    this.setId(i);
    this.setFirstName(firstName);
    this.setLastName(lastName);
    this.setRole(role);
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User(" + id + "," + firstName + "," + lastName + "," + role + ")";
  }

  @Override
  public boolean equals(Object obj) {
    User u = (User) obj;
    return super.equals(obj) && this.getRole().equals(u.getRole());
  }

}
