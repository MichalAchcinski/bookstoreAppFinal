package pl.achcinski.bookstore.logic;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.achcinski.bookstore.mapper.BookMapper;
import pl.achcinski.bookstore.model.book.Book;
import pl.achcinski.bookstore.model.book.BookRepository;
import pl.achcinski.bookstore.model.cartitem.CartItem;
import pl.achcinski.bookstore.model.cartitem.CartItemRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Log4j2
public class ShoppingCartService {
    final CartItemRepository cartItemRepository;
    final BookRepository bookRepository;
    final BookMapper bookMapper;


    public List<CartItem> listCartItems(String customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    public void deleteCartItemsByBook(Book book) {
        List<CartItem> cartItemList = cartItemRepository.findByBook(book);
        for (CartItem cartItem : cartItemList) {
            cartItemRepository.delete(cartItem);
        }
    }

    public void addItemCart(Integer bookId, String customerId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Book bookFromOptional;
        if (book.isPresent()) {
            bookFromOptional = book.get();
        } else {
            log.error("addItemCart error!");
            return;
        }
        CartItem cartItem;

        cartItem = new CartItem();
        cartItem.setCustomerId(customerId);
        cartItem.setBook(bookFromOptional);

        cartItemRepository.save(cartItem);

    }

    public double getAmountTotal(String customerId) {
        double total = 0;
        List<CartItem> listCart = listCartItems(customerId);
        for (CartItem cartItem : listCart) {
            total += cartItem.getBook().getPrice();
        }
        BigDecimal totalBD = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        return totalBD.doubleValue();
    }

}
