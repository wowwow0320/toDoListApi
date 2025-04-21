package to_do_list_API.TDL_API.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import to_do_list_API.TDL_API.domain.ToDoList;
import to_do_list_API.TDL_API.repository.ToDoListRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoListService {
    final private ToDoListRepository toDoListRepository;

    public ToDoList saveToDo(ToDoList toDoList, int userId, int categoryId) {
        toDoList.setUserId(userId);
        toDoList.setCategoryId(categoryId);
        toDoListRepository.save(toDoList);
        return toDoList;
    }
    public List<ToDoList> getToDoList(int userId, int categoryId){
        return toDoListRepository.findAllByUserIdAndCategoryId(userId, categoryId);
    }
}
