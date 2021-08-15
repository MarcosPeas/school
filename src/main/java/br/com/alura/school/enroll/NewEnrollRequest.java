package br.com.alura.school.enroll;

import br.com.alura.school.support.validation.Unique;
import br.com.alura.school.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewEnrollRequest {

    @Size(max = 20)
    @NotBlank
    @JsonProperty
    private final String username;

    public NewEnrollRequest() {
        this(null);
    }

    public NewEnrollRequest(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}
