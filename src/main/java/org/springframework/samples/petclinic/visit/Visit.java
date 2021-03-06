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
package org.springframework.samples.petclinic.visit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.BaseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * @author Ken Krebs
 */
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

  /**
   * Holds value of property date.
   */
  @Column(name = "visit_date")
  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;

  /**
   * Holds value of property description.
   */
  @Size(max = 8192)
  @Column(name = "description")
  private String description;

  private Integer petId;

  /**
   * Creates a new instance of Visit for the current date
   */
  public Visit() {
    this.date = new Date();
  }

  public Visit(int id, Integer petId, Date visit_date, String description) {
    this.setId(id);
    this.setPetId(petId);
    this.setDate(visit_date);
    this.setDescription(description);
  }

  /**
   * Getter for property date.
   *
   * @return Value of property date.
   */
  public Date getDate() {
    return this.date;
  }

  /**
   * Setter for property date.
   *
   * @param date
   *          New value of property date.
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * Getter for property description.
   *
   * @return Value of property description.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Setter for property description.
   *
   * @param description
   *          New value of property description.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Getter for property pet.
   *
   * @return Value of property pet.
   */
  public Integer getPetId() {
    return this.petId;
  }

  /**
   * Setter for property pet.
   *
   * @param pet
   *          New value of property pet.
   */
  public void setPetId(Integer petId) {
    this.petId = petId;
  }

  @Override
  public String toString() {
    return new ToStringCreator(this).append("id", this.getId()).append("petId", this.getPetId())
        .append("date", this.getDate()).toString();
  }

}
