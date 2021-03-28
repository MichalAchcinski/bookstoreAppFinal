package pl.achcinski.bookstore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.achcinski.bookstore.model.order.ShippingInformation;
import pl.achcinski.bookstore.model.order.ShippingInformationRepository;

@Repository
public interface SqlShippingInformationRepository extends ShippingInformationRepository, JpaRepository<ShippingInformation, Integer> {
}
