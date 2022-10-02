package com.bookstore.rubens.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private LocalDate launchDate;

    @Column(nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<RentModel> rents;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherModel publisher;
}
