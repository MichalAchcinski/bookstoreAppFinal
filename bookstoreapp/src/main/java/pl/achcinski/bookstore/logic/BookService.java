package pl.achcinski.bookstore.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.achcinski.bookstore.mapper.BookMapper;
import pl.achcinski.bookstore.model.book.Book;
import pl.achcinski.bookstore.model.book.BookRepository;
import pl.achcinski.bookstore.model.book.Category;
import pl.achcinski.bookstore.model.dto.BookDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> getBooks() {
        return bookMapper.bookToBookDtoList(bookRepository.findAll());
    }

    public void saveBook(Book book, MultipartFile file, String title, String author, String description,
                         String publisher, LocalDate publishedOn, Category category, int pages, float price) {
        if (!bookRepository.existsById(book.getId())) {
            book = new Book();
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            System.out.println("Niepoprawny plik image");
        }
        try {
            book.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPublisher(publisher);
        book.setPublishedOn(publishedOn);
        book.setCategory(category);
        book.setPages(pages);
        book.setPrice(price);

        bookRepository.save(book);
    }


}
