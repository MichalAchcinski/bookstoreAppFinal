package pl.achcinski.bookstore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.achcinski.bookstore.model.order.Order;
import pl.achcinski.bookstore.model.order.OrderRepository;

@Repository
public interface SqlOrderRepository extends OrderRepository, JpaRepository<Order, Integer> {
}
