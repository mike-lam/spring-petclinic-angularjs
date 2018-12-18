package org.springframework.samples.petclinic.user;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.Person;

@Entity
public class User extends Person {

  public User() {
  }

  public User(int i, String firstName, String lastName) {
    this.setId(i);
    this.setFirstName(firstName);
    this.setLastName(lastName);
  }

}
