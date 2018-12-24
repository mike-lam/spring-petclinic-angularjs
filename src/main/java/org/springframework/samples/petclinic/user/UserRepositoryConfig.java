package org.springframework.samples.petclinic.user;

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
    User user;
    user = new User(1, "Michel", "Carter", "papa");
    userRepository.save(user);
    user = new User(2, "Carole", "Leary", "maman");
    userRepository.save(user);
    user = new User(3, "Daphnee", "Dougla", "child");
    userRepository.save(user);
    user = new User(4, "Stephane", "Ortega", "child");
    userRepository.save(user);
    user = new User(5, "Carole", "AAALeary", "guest");
    userRepository.save(user);
  }

  @Autowired
  private RepositoryRestConfiguration repositoryRestConfiguration;

  @PostConstruct
  public void exposeIds() {
    // this.repositoryRestConfiguration.setReturnBodyForPutAndPost(true);
    this.repositoryRestConfiguration.exposeIdsFor(User.class);
  }

}
