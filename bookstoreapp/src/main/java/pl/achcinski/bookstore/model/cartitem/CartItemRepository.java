package pl.achcinski.bookstore.model.cartitem;

import pl.achcinski.bookstore.model.book.Book;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {

    List<CartItem> findByCustomerId(String customerId);

    List<CartItem> findByBook(Book book);

    CartItem findByCustomerIdAndBook(String customerId, Book book);

    Optional<CartItem> findById(Integer id);

    CartItem save(CartItem entity);

    void delete(CartItem entity);

}
