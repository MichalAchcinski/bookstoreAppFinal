package pl.achcinski.bookstore.model.cartitem;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.achcinski.bookstore.model.book.Book;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CART_ITEMS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String customerId;

    private LocalDateTime createdOn;


    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
    }


}
