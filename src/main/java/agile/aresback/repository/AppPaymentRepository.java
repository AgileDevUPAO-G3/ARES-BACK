package agile.aresback.repository;

import agile.aresback.model.entity.AppPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppPaymentRepository extends JpaRepository<AppPayment, Long> {
    Optional<AppPayment> findByExternalReference(String externalReference);
}
