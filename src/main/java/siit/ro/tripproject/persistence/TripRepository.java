package siit.ro.tripproject.persistence;

import org.springframework.data.repository.CrudRepository;
import siit.ro.tripproject.model.Trip;
import siit.ro.tripproject.model.User;
import javax.transaction.Transactional;
import java.util.Set;




public interface TripRepository extends CrudRepository <Trip, Long> {

//    Set<Trip> findByUser(User user);

}
