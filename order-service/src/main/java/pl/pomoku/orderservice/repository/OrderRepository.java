package pl.pomoku.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pomoku.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
