package pl.achcinski.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.achcinski.bookstore.logic.BookService;
import pl.achcinski.bookstore.logic.ShoppingCartService;
import pl.achcinski.bookstore.mapper.BookMapper;
import pl.achcinski.bookstore.model.book.Book;
import pl.achcinski.bookstore.model.book.BookRepository;
import pl.achcinski.bookstore.model.cartitem.CartItemRepository;
import pl.achcinski.bookstore.model.dto.BookDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
@Log4j2
public class WebBookController {
    final BookRepository bookRepository;
    final BookService bookService;
    final BookMapper bookMapper;
    final CartItemRepository cartItemRepository;
    final ShoppingCartService shoppingCartService;

    @GetMapping
    String showBooks() {
        return "books";
    }

    @GetMapping("/{id}")
    String showBook(@PathVariable("id") Integer id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        Book bookFromOptional = null;
        if (book.isPresent()) {
            bookFromOptional = book.get();
        } else {
            log.error("getMapping single error!");
        }

        BookDto currentDto = bookMapper.bookToBookDto(bookFromOptional);
        String title = currentDto.getTitle();
        model.addAttribute("bookSingle", currentDto);
        model.addAttribute("bookTitle", title);
        return "bookSingle";
    }

    @GetMapping("/addbook")
    String addBook(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("addbook", bookDto);
        return "bookAdd";
    }

    @PostMapping("/savebook")
    String saveBook(@ModelAttribute("savebook") @Valid Book saveBook, BindingResult bindingResult,
                    RedirectAttributes redirAttrs, @RequestParam("fileImage") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "redirect:/books";
        }
        bookService.saveBook(saveBook, file, saveBook.getTitle(), saveBook.getAuthor(),
                saveBook.getDescription(), saveBook.getPublisher(),
                saveBook.getPublishedOn(), saveBook.getCategory(), saveBook.getPages(), saveBook.getPrice());
        BookDto saveBookDto = bookMapper.bookToBookDto(saveBook);
        String result = "Dodano książkę: " + saveBookDto.getTitle();
        redirAttrs.addFlashAttribute("message", result);
        return "redirect:/books";
    }


    @GetMapping(value = "/editbook/{id}")
    String editBook(@PathVariable("id") Integer id, Model model) {
        Book current = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        BookDto currentDto = bookMapper.bookToBookDto(current);
        model.addAttribute("editbook", currentDto);
        return "bookUpdate";
    }

    @PostMapping(value = "/updatebook")
    String updateBook(@ModelAttribute("updatebook") @Valid Book updatebook, BindingResult bindingResult,
                      RedirectAttributes redirAttrs, @RequestParam("fileImage") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "redirect:/books";
        }
        bookService.saveBook(updatebook, file, updatebook.getTitle(), updatebook.getAuthor(),
                updatebook.getDescription(), updatebook.getPublisher(),
                updatebook.getPublishedOn(), updatebook.getCategory(), updatebook.getPages(), updatebook.getPrice());
        BookDto updateBookDto = bookMapper.bookToBookDto(updatebook);
        String result = "Zaktualizowano książkę: " + updateBookDto.getTitle();
        redirAttrs.addFlashAttribute("message", result);
        return "redirect:/books";
    }


    @GetMapping("/deletebook/{id}")
    String deleteBook(@PathVariable("id") int id, RedirectAttributes redirAttrs) {
        Book current = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        shoppingCartService.deleteCartItemsByBook(current);
        bookRepository.delete(current);
        BookDto currentDto = bookMapper.bookToBookDto(current);
        String result = "Usunięto książkę: " + currentDto.getTitle();
        redirAttrs.addFlashAttribute("message", result);
        return "redirect:/books";
    }

    @ModelAttribute("books")
    List<BookDto> getBooks() {
        return bookMapper.bookToBookDtoList(bookRepository.findAll());
    }


}
