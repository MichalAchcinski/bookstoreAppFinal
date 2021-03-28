package pl.achcinski.bookstore.model.order;

import java.util.List;
import java.util.Optional;

public interface ShippingInformationRepository {

    List<ShippingInformation> findAll();

    Optional<ShippingInformation> findById(Integer id);

    Optional<ShippingInformation> findByCustomerId(String customerId);

    boolean existsById(Integer id);

    boolean existsByCustomerId(String customerId);

    ShippingInformation save(ShippingInformation entity);

    void delete(ShippingInformation entity);

}
