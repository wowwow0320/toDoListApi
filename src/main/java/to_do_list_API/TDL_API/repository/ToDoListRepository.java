package to_do_list_API.TDL_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import to_do_list_API.TDL_API.domain.Category;
import to_do_list_API.TDL_API.domain.ToDoList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    List<ToDoList> findAllByUserIdAndCategoryIdAndCategoryName(int userId, int categoryId, String categoryName);
    Optional<ToDoList> findByPid(int pid);
    void deleteByPid(int pid);

    List<ToDoList> findAllByUserIdAndCategoryId(int userId, int categoryId);
}
