package org.springframework.samples.petclinic.vet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.model.Person;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vets")
public class Vet extends Person {

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
  // @ManyToOne(cascade = CascadeType.ALL, optional = true) // as defined in schema
  // @JoinColumn(name = "type_id")
  @JsonIgnore
  private Set<Specialty> specialties;

  public Vet() {
  }

  public Vet(int i, String firstName, String lastName) {
    this.setId(i);
    this.setFirstName(firstName);
    this.setLastName(lastName);
  }

  protected void setSpecialtiesInternal(Set<Specialty> specialties) {
    this.specialties = specialties;
  }

  protected Set<Specialty> getSpecialtiesInternal() {
    if (this.specialties == null) {
      this.specialties = new HashSet<>();
    }
    return this.specialties;
  }

  // @XmlElement
  public List<Specialty> getSpecialties() {
    List<Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
    PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
    return Collections.unmodifiableList(sortedSpecs);
  }

  public int getNrOfSpecialties() {
    return getSpecialtiesInternal().size();
  }

  public void addSpecialty(Specialty specialty) {
    getSpecialtiesInternal().add(specialty);
  }

}
