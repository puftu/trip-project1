package siit.ro.tripproject.persistence;

import org.springframework.data.repository.CrudRepository;

import siit.ro.tripproject.model.User;

import javax.transaction.Transactional;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
