package kristian9577.springdata.repositories;

import kristian9577.springdata.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Integer> {
}
