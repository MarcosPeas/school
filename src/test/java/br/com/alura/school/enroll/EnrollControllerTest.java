package br.com.alura.school.enroll;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EnrollControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollRepository enrollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void should_add_new_enroll() throws Exception {
        userRepository.save(new User("ana", "ana@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("ana", new BigDecimal(20));

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_not_allow_duplication_of_enroll() throws Exception {
        User user = userRepository.save(new User("ana", "ana@email.com"));
        Course course = courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        enrollRepository.save(new Enroll(course, user, new BigDecimal(20)));

        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("ana", new BigDecimal(20));

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_retrieve_report() throws Exception {
        User user = userRepository.save(new User("ana", "ana@email.com"));
        Course course = courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        enrollRepository.save(new Enroll(course, user, new BigDecimal(20)));

        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].email", is("ana@email.com")))
                .andExpect(jsonPath("$[0].quantidade_matriculas", is(1)));
    }

    @Test
    void not_content_when_enrollment_does_not_exist() throws Exception {
        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void not_found_when_user_does_not_exist() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("ana", new BigDecimal(20));

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void not_found_when_course_does_not_exist() throws Exception {
        userRepository.save(new User("ana", "ana@email.com"));
        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("ana", new BigDecimal(20));

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void bad_request_when_the_price_is_less_than_5() throws Exception {
        userRepository.save(new User("ana", "ana@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("ana", new BigDecimal(4));

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isBadRequest());
    }

}