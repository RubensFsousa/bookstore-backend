package com.bookstore.rubens.io.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Long id;

    private String name;

    private String author;

    private LocalDate launchDate;

    private Integer amount;

    private Long publisher;

}
