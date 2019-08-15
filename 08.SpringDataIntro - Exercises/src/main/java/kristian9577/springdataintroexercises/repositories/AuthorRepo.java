package kristian9577.springdataintroexercises.repositories;

import kristian9577.springdataintroexercises.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Integer> {
}
