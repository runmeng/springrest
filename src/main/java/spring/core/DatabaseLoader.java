package spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import spring.rest.course.Course;
import spring.rest.course.CourseRepository;
import spring.rest.review.Review;
import spring.user.User;
import spring.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final CourseRepository courses;
    private final UserRepository users;

    @Autowired
    public DatabaseLoader(CourseRepository courses, UserRepository users) {
        this.courses = courses;
        this.users = users;
    }


    @Override
     public void run(ApplicationArguments args) throws Exception {
        Course course = new Course("Java Basics","https://www.teamtreehouse.com/library/java-basics");
        course.addReview(new Review(5,"You are fabulous!"));
        courses.save(course);
        String [] templates = {
                "Up and Running with %s",
                "%s Basics",
                "%s for neckbeards",
                "Under the hood: %s"
        };
        String [] buzzwords = {
                "Spring Rest Data",
                "Java 9",
                "Scala",
                "Groovy",
                "Hibernate"
        };

        List<User> students = Arrays.asList(
                new User("jacobproffer", "Jacob",  "Proffer", "password", new String[] {"ROLE_USER"}),
                new User("mlnorman", "Mike",  "Norman", "password", new String[] {"ROLE_USER"}),
                new User("k_freemansmith", "Karen",  "Freeman-Smith", "password", new String[] {"ROLE_USER"}),
                new User("seth_lk", "Seth",  "Kroger", "password", new String[] {"ROLE_USER"}),
                new User("mrstreetgrid", "Java",  "Vince", "password", new String[] {"ROLE_USER"}),
                new User("anthonymikhail", "Tony",  "Mikhail", "password", new String[] {"ROLE_USER"}),
                new User("boog690", "AJ",  "Teacher", "password", new String[] {"ROLE_USER"}),
                new User("faelor", "Erik",  "Faelor Shafer", "password", new String[] {"ROLE_USER"}),
                new User("christophernowack", "Christopher",  "Nowack", "password", new String[] {"ROLE_USER"}),
                new User("calebkleveter", "Caleb",  "Kleveter", "password", new String[] {"ROLE_USER"}),
                new User("richdonellan", "Rich",  "Donnellan", "password", new String[] {"ROLE_USER"}),
                new User("albertqerimi", "Albert",  "Qerimi", "password", new String[] {"ROLE_USER"})
        );

        users.save(students);
        users.save(new User("guazi","Ryan","Meng", "123321",new String[]{"ROLE_USER","ROLE_ADMIN"}));

        List<Course> manyCourses= new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i ->{
                    String temp = templates[i%templates.length];
                    String buzz = buzzwords[i%buzzwords.length];
                    String title = String.format(temp,buzz);

                    Course c = new Course(title,"https://nba.hupu.com/");
                    Review review = new Review(i % 5, String.format("Moar %s please!", buzz));
                    review.setReviewer(students.get(i%students.size()));
                    c.addReview(review);
                    manyCourses.add(c);
                });
         courses.save(manyCourses);
    }
 }
