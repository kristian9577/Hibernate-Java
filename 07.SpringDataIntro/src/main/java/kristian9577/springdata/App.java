package kristian9577.springdata;

import kristian9577.springdata.entities.Student;
import kristian9577.springdata.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class App implements CommandLineRunner {

    @Autowired
    private StudentRepo studentRepo;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello Word");
        Student student = new Student();


    }
}
