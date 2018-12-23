package org.springframework.samples.petclinic.owner;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.visit.Visit;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @ManyToOne(cascade = CascadeType.ALL, optional = true) // as defined in schema
  @JoinColumn(name = "type_id")
  @JsonIgnore
  private PetType type;

  @ManyToOne(cascade = CascadeType.ALL, optional = true) // as defined in schema
  @JoinColumn(name = "owner_id")
  @JsonIgnore
  private Owner owner;

  @OneToMany(cascade = CascadeType.ALL) // , mappedBy = "pet") // , fetch = FetchType.EAGER)
  @JoinColumn(name = "visit_id")
  private Set<Visit> visits;

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
