package to_do_list_API.TDL_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import to_do_list_API.TDL_API.domain.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByUserIdAndDate( int userId, LocalDate date);
    boolean existsByUserIdAndDate(int userId, LocalDate date);  // 중복 방지용
}
