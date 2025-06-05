package agile.aresback.repository;

import agile.aresback.model.entity.User;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);

}
