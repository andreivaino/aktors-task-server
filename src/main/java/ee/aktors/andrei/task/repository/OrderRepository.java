package ee.aktors.andrei.task.repository;

import ee.aktors.andrei.task.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



}
