package ee.aktors.andrei.task.repository;

import ee.aktors.andrei.task.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPersonalId(Long personalId);

}
