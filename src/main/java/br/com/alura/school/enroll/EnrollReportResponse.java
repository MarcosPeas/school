package br.com.alura.school.enroll;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollReportResponse {

    @JsonProperty
    private final String email;

    @JsonProperty("quantidade_matriculas")
    private final long quantidadeMatriculas;

    public EnrollReportResponse() {
        this(null, 0);
    }

    public EnrollReportResponse(String email, long quantidadeMatriculas) {
        this.email = email;
        this.quantidadeMatriculas = quantidadeMatriculas;
    }



}
