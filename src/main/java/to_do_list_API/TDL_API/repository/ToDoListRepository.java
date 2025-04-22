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
    List<ToDoList> findAllByUserIdAndCategoryId(int userId, int categoryId);
    Optional<ToDoList> findByPid(int pid);
    void deleteByPid(int pid);

    int countByUserIdAndCategoryIdIn(int userId, List<Integer> categoryIds);
}
