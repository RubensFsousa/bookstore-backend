package com.bookstore.rubens.model.io.response;

import com.bookstore.rubens.model.PublisherModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private PublisherModel publisher;

}
