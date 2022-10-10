package com.bookstore.rubens.repository;

import com.bookstore.rubens.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {

    Optional<BookModel> findByName(String name);

    Optional<BookModel> findByPublisherId(Long id);
}
