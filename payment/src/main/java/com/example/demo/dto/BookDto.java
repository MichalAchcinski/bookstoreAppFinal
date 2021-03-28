package com.example.demo.dto;

import com.example.demo.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @Min(value = 1, message = "Id zawsze jest wieksze od 0")
    private int id;

    @NotBlank(message = "Pole 'tytuł' nie może być puste!")
    private String title;

    @NotBlank(message = "Pole 'autor' nie może być puste!")
    private String author;

    @Lob
    private String image;

    private String description;
    private String publisher;
    private LocalDate publishedOn;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Min(value = 1, message = "Ilość stron musi być większa od 0")
    private int pages;

    @Min(value = 0, message = "Cena nie może być ujemna")
    private float price;

}
