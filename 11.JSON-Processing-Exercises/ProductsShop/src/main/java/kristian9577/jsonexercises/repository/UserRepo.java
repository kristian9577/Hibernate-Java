package kristian9577.jsonexercises.repository;

import kristian9577.jsonexercises.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepo extends JpaRepository<User,Integer> {
}
