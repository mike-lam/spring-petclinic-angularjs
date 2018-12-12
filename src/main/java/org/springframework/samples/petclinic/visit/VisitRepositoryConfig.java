package org.springframework.samples.petclinic.visit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class VisitRepositoryConfig {

  @Autowired
  private VisitRepository visitRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    preloadVisits();
  }

  private void preloadVisits() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Visit visit;
    try {
      visit = new Visit(1, 1, df.parse("2013-01-01"), "rabies shot");
      visitRepository.save(visit);
      visit = new Visit(2, 8, df.parse("2013-01-02"), "rabies shot");
      visitRepository.save(visit);
      visit = new Visit(3, 8, df.parse("2013-01-03"), "neutered");
      visitRepository.save(visit);
      visit = new Visit(4, 7, df.parse("2013-01-04"), "spayed");
      visitRepository.save(visit);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

}
