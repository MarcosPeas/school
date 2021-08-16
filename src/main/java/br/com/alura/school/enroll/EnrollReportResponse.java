package br.com.alura.school.enroll;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollReportResponse {

    @JsonProperty
    private String email;

    @JsonProperty("quantidade_matriculas")
    private long quantidadeMatriculas;

    public EnrollReportResponse() {
    }

    public EnrollReportResponse(EnrollReport enrollReport) {
        this.email = enrollReport.getEmail();
        this.quantidadeMatriculas = enrollReport.getEnrollCount();
    }


}
