package pl.achcinski.bookstore.mapper;

import org.mapstruct.Mapper;
import pl.achcinski.bookstore.model.book.Book;
import pl.achcinski.bookstore.model.dto.BookDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookToBookDto(Book book);

    List<BookDto> bookToBookDtoList(List<Book> bookList);


}
