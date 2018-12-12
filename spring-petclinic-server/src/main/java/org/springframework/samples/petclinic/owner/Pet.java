/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.samples.petclinic.model.NamedEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @ManyToOne
  @JoinColumn(name = "type_id")
  private PetType type;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  @JsonIgnore
  private Owner owner;

  public Pet() {
  }

  public Pet(int i, String name, Date birthDate, PetType type, Owner owner) {
    this.setId(i);
    this.setName(name);
    this.setBirthDate(birthDate);
    this.setOwner(owner);
    this.setType(type);
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public Date getBirthDate() {
    return this.birthDate;
  }

  public void setType(PetType type) {
    this.type = type;
  }

  public PetType getType() {
    return this.type;
  }

  protected void setOwner(Owner owner) {
    this.owner = owner;
  }

  public Owner getOwner() {
    return this.owner;
  }

}
