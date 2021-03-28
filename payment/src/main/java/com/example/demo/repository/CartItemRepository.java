package com.example.demo.repository;



import com.example.demo.entity.Book;
import com.example.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCustomerId(String customerId);

    List<CartItem> findByBook(Book book);

    CartItem findByCustomerIdAndBook(String customerId, Book book);

    Optional<CartItem> findById(Integer id);

    CartItem save(CartItem entity);

    void delete(CartItem entity);

}
