package kristian9577.springdataintroexercises.repositories;

import kristian9577.springdataintroexercises.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
