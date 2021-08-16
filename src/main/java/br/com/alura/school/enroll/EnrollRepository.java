package br.com.alura.school.enroll;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    Optional<Enroll> findByUserAndCourse(User user, Course course);

    @Query(value = "SELECT NEW br.com.alura.school.enroll.EnrollReport(e.user.username, " +
            "e.user.email, " +
            "COUNT(e.user.email)) " +
            "FROM Enroll e GROUP BY e.user.email " +
            "ORDER BY COUNT(e.user.email) DESC")
    List<EnrollReport> report();
}
