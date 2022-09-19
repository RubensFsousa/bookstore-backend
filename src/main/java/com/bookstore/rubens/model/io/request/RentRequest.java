package com.bookstore.rubens.model.io.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequest {

    @NotNull
    private LocalDate rentDate;

    @NotNull
    private LocalDate rentPredict;

    @NotNull
    private Long bookId;

    @NotNull
    private Long userId;
}

