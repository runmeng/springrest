package spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import spring.rest.course.Course;
import spring.rest.course.CourseRepository;
import spring.rest.review.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final CourseRepository courses;

    @Autowired
    public DatabaseLoader(CourseRepository courses) {
        this.courses = courses;
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
        List<Course> manyCourses= new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i ->{
                    String temp = templates[i%templates.length];
                    String buzz = buzzwords[i%buzzwords.length];
                    String title = String.format(temp,buzz);

                    Course c = new Course(title,"https://nba.hupu.com/");
                    c.addReview(new Review(i%5,String.format("Moar %s please!",buzz)));
                    manyCourses.add(c);
                });
         courses.save(manyCourses);
    }
 }
