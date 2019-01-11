package org.springframework.samples.petclinic.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@Configuration
public class UserRepositoryConfig {

  @Autowired
  private UserRepository userRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    preloadUsers();
  }

  private void preloadUsers() {
    List<User> lst = new ArrayList<User>();
    lst.add(new User(null, "Michel", "Carter", "papa"));
    lst.add(new User(null, "Carole", "Leary", "maman"));
    lst.add(new User(null, "Daphnee", "Dougla", "child"));
    lst.add(new User(null, "Stephane", "Ortega", "child"));
    lst.add(new User(null, "Carole", "AAALeary", "guest"));
    userRepository.saveAll(lst);
  }

  @Autowired
  private RepositoryRestConfiguration repositoryRestConfiguration;

  @PostConstruct
  public void exposeIds() {
    // this.repositoryRestConfiguration.setReturnBodyForPutAndPost(true);
    this.repositoryRestConfiguration.exposeIdsFor(User.class);
  }

}
