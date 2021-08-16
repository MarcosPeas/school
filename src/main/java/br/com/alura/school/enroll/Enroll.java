package br.com.alura.school.enroll;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Enroll {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal price;

    public Enroll() {
    }

    public Enroll(Course course, User user, BigDecimal price) {
        this.course = course;
        this.user = user;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Course getCourse() {
        return course;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
