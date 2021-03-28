package pl.achcinski.bookstore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.achcinski.bookstore.model.book.Book;
import pl.achcinski.bookstore.model.book.BookRepository;

@Repository
public interface SqlBookRepository extends BookRepository, JpaRepository<Book, Integer> {
}
