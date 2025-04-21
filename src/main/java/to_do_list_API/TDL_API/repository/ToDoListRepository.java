package to_do_list_API.TDL_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import to_do_list_API.TDL_API.domain.Category;
import to_do_list_API.TDL_API.domain.ToDoList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findAllByUserIdAndCategoryId(int userId, int categoryId);
    Optional<ToDoList> findByPid(int pid);
    Optional<ToDoList> deleteByPid(int pid);
}
