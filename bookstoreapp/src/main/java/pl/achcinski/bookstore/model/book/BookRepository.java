package pl.achcinski.bookstore.model.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Integer id);

    Optional<Book> findByTitle(String title);

    boolean existsById(Integer id);

    Book save(Book entity);

    void delete(Book entity);

    Page<Book> findAll(Pageable page);

}
