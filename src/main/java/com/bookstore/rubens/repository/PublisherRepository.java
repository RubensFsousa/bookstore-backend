package com.bookstore.rubens.repository;

import com.bookstore.rubens.model.PublisherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherModel, Long> {

    Optional<PublisherModel> findByName(String name);
}
