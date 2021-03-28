package com.example.demo.repository;


import com.example.demo.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, Integer> {

    List<Book> findAll();

    Optional<Book> findById(Integer id);

    Optional<Book> findByTitle(String title);

    boolean existsById(Integer id);

    Book save(Book entity);

    void delete(Book entity);

    Page<Book> findAll(Pageable page);

}
