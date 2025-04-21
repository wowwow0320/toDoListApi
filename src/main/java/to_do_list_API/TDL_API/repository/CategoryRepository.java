package to_do_list_API.TDL_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import to_do_list_API.TDL_API.domain.Category;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    //Optional<Category> findByDate(LocalDate date);
    Optional<Category> findByDateAndUserId(LocalDate date, int userId);
}
