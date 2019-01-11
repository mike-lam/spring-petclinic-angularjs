package org.springframework.samples.petclinic.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.samples.petclinic.test.AbstractRestControllerTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureTestEntityManager
public class UserRepositoryTests extends AbstractRestControllerTest {

  @Autowired
  protected UserRepository userRepository;

  @Test
  public void crud() throws Exception {
    long c = userRepository.count();
    User u0, u1;
    u0 = userRepository.save(new User(null, "first", "last", "role")); // create
    assertEquals(c + 1, userRepository.count());
    assertNotNull(u0.getId());
    u1 = userRepository.findById(u0.getId()).get();
    assertEquals(u0, u1);
    u0 = new User(u0.getId(), "FIRST", "LAST", "ROLE");
    u1 = userRepository.save(u0); // update
    assertEquals(u0, u1);
    userRepository.deleteById(u0.getId());
    assertEquals(c, userRepository.count());
    assertFalse(userRepository.existsById(u0.getId()));
  }
  //
  // public <S extends User> Optional<S> findOne(Example<S> example) {
  // return userRepository.findOne(example);
  // }
  //
  // public Page<User> findAll(Pageable pageable) {
  // return userRepository.findAll(pageable);
  // }
  //
  // public List<User> findAll() {
  // return userRepository.findAll();
  // }
  //
  // public List<User> findAll(Sort sort) {
  // return userRepository.findAll(sort);
  // }
  //
  // public Optional<User> findById(Integer id) {
  // return userRepository.findById(id);
  // }
  //
  // public List<User> findAllById(Iterable<Integer> ids) {
  // return userRepository.findAllById(ids);
  // }
  //
  // public <S extends User> List<S> saveAll(Iterable<S> entities) {
  // return userRepository.saveAll(entities);
  // }
  //
  // public boolean existsById(Integer id) {
  // return userRepository.existsById(id);
  // }
  //
  // public void flush() {
  // userRepository.flush();
  // }
  //
  // public <S extends User> S saveAndFlush(S entity) {
  // return userRepository.saveAndFlush(entity);
  // }
  //
  // public void deleteInBatch(Iterable<User> entities) {
  // userRepository.deleteInBatch(entities);
  // }
  //
  // public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
  // return userRepository.findAll(example, pageable);
  // }
  //
  // public long count() {
  // return userRepository.count();
  // }
  //
  // public void deleteAllInBatch() {
  // userRepository.deleteAllInBatch();
  // }
  //
  // public void deleteById(Integer id) {
  // userRepository.deleteById(id);
  // }
  //
  // public User getOne(Integer id) {
  // return userRepository.getOne(id);
  // }
  //
  // public void delete(User entity) {
  // userRepository.delete(entity);
  // }
  //
  // public <S extends User> long count(Example<S> example) {
  // return userRepository.count(example);
  // }
  //
  // public void deleteAll(Iterable<? extends User> entities) {
  // userRepository.deleteAll(entities);
  // }
  //
  // public <S extends User> List<S> findAll(Example<S> example) {
  // return userRepository.findAll(example);
  // }
  //
  // public <S extends User> boolean exists(Example<S> example) {
  // return userRepository.exists(example);
  // }
  //
  // public void deleteAll() {
  // userRepository.deleteAll();
  // }
  //
  // public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
  // return userRepository.findAll(example, sort);
  // }

}