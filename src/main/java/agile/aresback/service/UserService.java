package agile.aresback.service;

import agile.aresback.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User save(User user);

    List<User> findAll();

    Optional<User> findById(Integer id);

    void deleteById(Integer id);
}
