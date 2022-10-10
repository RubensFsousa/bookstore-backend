package com.bookstore.rubens.model.io.response;


import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Enum.StatusRent;
import com.bookstore.rubens.model.UserModel;
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
public class RentResponse {

    private Long id;

    private LocalDate rentDate;

    private LocalDate rentPredict;

    private LocalDate devolutionDate;

    private StatusRent status;

    private BookModel book;

    private UserModel user;
}
