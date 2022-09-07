package com.bookstore.rubens.io.response;


import com.bookstore.rubens.model.Enum.StatusRent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private Long book;

    private Long user;
}
