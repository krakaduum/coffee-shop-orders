package coffeeshoporders.repository;

import coffeeshoporders.model.order_events.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<OrderEvent, Long> {

    Collection<OrderEvent> findAllByOrderId(String orderId);

    Optional<OrderEvent> findFirstByOrderIdOrderByVersionDesc(String orderId);

}
