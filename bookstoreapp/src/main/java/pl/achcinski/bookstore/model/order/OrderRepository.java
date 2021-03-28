package pl.achcinski.bookstore.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findByCustomerId(String customerId);

    Optional<Order> findById(Integer id);

    Order save(Order entity);

    void delete(Order entity);

}
