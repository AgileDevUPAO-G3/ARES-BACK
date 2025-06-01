package agile.aresback.repository;

import agile.aresback.model.entity.Client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByDni(String dni);

}
