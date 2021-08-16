package br.com.alura.school.enroll;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class EnrollController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollRepository enrollRepository;

    public EnrollController(UserRepository userRepository, CourseRepository courseRepository, EnrollRepository enrollRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollRepository = enrollRepository;
    }

    @PostMapping("/courses/{courseCode}/enroll")
    ResponseEntity<Object> newEnroll(@PathVariable String courseCode, @RequestBody @Valid NewEnrollRequest newEnrollRequest) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Course %s not found", courseCode)));
        User user = userRepository.findByUsername(newEnrollRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("User %s not found", newEnrollRequest.getUsername())));

        Optional<Enroll> optional = enrollRepository.findByUserAndCourse(user, course);
        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        enrollRepository.save(new Enroll(course, user, newEnrollRequest.getPrice()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/courses/enroll/report")
    ResponseEntity<List<EnrollReportResponse>> report() {
        List<EnrollReportResponse> report = enrollRepository.report()
                .stream().map(EnrollReportResponse::new).collect(Collectors.toList());

        if (report.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(report);
    }
}
