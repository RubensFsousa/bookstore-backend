package com.bookstore.rubens.model.io.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    private String author;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate launchDate;

    @NotNull
    private Long publisherId;
}
