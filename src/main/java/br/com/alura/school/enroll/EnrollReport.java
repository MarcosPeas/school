package br.com.alura.school.enroll;

public class EnrollReport {

    private String username;
    private String email;
    private long enrollCount;

    public EnrollReport(String username, String email, long enrollCount) {
        this.username = username;
        this.email = email;
        this.enrollCount = enrollCount;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public long getEnrollCount() {
        return enrollCount;
    }
}
