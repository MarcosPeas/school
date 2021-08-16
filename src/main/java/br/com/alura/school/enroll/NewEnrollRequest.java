package br.com.alura.school.enroll;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class NewEnrollRequest {

    @Size(max = 20)
    @NotBlank
    @JsonProperty
    private final String username;

    @Min(value = 5, message = "The price cannot be less than 5")
    @NotNull(message = "The price cannot be less than 5")
    private final BigDecimal price;

    public NewEnrollRequest() {
        this(null, null);
    }

    public NewEnrollRequest(String username, BigDecimal price) {
        this.username = username;
        this.price = price;
    }

    String getUsername() {
        return username;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
