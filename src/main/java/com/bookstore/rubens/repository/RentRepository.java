package com.bookstore.rubens.repository;

import com.bookstore.rubens.model.RentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RentRepository extends JpaRepository<RentModel, Long> { ;
}


