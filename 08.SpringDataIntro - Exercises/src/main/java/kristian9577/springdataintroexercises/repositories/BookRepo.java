package kristian9577.springdataintroexercises.repositories;

import kristian9577.springdataintroexercises.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {
    List<Book>findAllByRealeseDateAfter(LocalDate date);

    List<Book>findAllByRealeseDateBefore(LocalDate date);
}
