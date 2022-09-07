package com.bookstore.rubens.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "publishers")
public class PublisherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "publisher",cascade = CascadeType.ALL, fetch =FetchType.LAZY )
    private List<BookModel> books;
}
