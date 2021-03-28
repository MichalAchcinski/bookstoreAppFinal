package pl.achcinski.bookstore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.achcinski.bookstore.model.cartitem.CartItem;
import pl.achcinski.bookstore.model.cartitem.CartItemRepository;

@Repository
public interface SqlCartItemRepository extends CartItemRepository, JpaRepository<CartItem, Integer> {
}
