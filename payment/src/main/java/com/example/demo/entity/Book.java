package com.example.demo.entity;

import com.example.demo.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Pole 'tytuł' nie może być puste")
    private String title;

    @NotBlank(message = "Pole 'autor' nie może być puste")
    private String author;

    @Lob
    private String image;

    private String description;
    private String publisher;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate publishedOn;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Min(value = 1, message = "Ilość stron musi być większa od 0")
    private int pages;

    @Min(value = 0, message = "Cena nie może być ujemna")
    private float price;

//    @Min(value = 0,message = "Minimalna liczba książek nie może być ujemna")
//    private int count;
//
//    @Min(value = 0,message = "Minimalna liczba sprzedanmych książek nie może być ujemna")
//    private int sold;

    public Book(String title, String author, String description, String publisher, LocalDate publishedOn, Category category, int pages, float price) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.publishedOn = publishedOn;
        this.category = category;
        this.pages = pages;
        this.price = price;
    }


    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedOn = LocalDateTime.now();
    }

}
