package br.com.alura.school.enroll;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

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
    ResponseEntity<Object> newUser(@PathVariable String courseCode, @RequestBody @Valid NewEnrollRequest newEnrollRequest) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("User %s not found", newEnrollRequest.getUsername())));
        User user = userRepository.findByUsername(newEnrollRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Course %s not found", courseCode)));

        Optional<Enroll> optional = enrollRepository.findByUserAndCourse(user, course);
        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        enrollRepository.save(new Enroll(LocalDateTime.now(), course, user));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
