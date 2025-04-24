package to_do_list_API.TDL_API.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import to_do_list_API.TDL_API.domain.Category;
import to_do_list_API.TDL_API.domain.ToDoList;
import to_do_list_API.TDL_API.repository.CategoryRepository;
import to_do_list_API.TDL_API.repository.ToDoListRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoListService {
    final private CategoryRepository categoryRepository;
    final private ToDoListRepository toDoListRepository;

    public ToDoList saveToDo(ToDoList toDoList, int userId, int categoryId,String categoryName) {
        toDoList.setUserId(userId);
        toDoList.setCategoryId(categoryId);
        toDoList.setCategoryName(categoryName);
        toDoListRepository.save(toDoList);
        return toDoList;
    }
    public List<ToDoList> getToDoList(int userId, int categoryId, String categoryName){
        return toDoListRepository.findAllByUserIdAndCategoryIdAndCategoryName(userId, categoryId, categoryName);
    }
    public Optional<ToDoList> updateToDoListCheckedStatus(int pid) {
        // pid로 ToDoList 찾기
        Optional<ToDoList> toDoListOpt = toDoListRepository.findByPid(pid);

        if (toDoListOpt.isPresent()) {
            ToDoList toDoList = toDoListOpt.get();
            toDoList.setChecked(true);  // isChecked 값을 true로 설정 (1로 변경)

            // 변경된 객체 저장
            return Optional.of(toDoListRepository.save(toDoList));
        }

        return Optional.empty(); // 해당 pid에 대한 ToDoList가 없을 경우
    }
    @Transactional
    public Optional<ToDoList> deleteToDoList(int pid){
        Optional<ToDoList> toDoListOpt = toDoListRepository.findByPid(pid);
        if (toDoListOpt.isPresent()) {
            toDoListRepository.deleteByPid(pid);  // 삭제
        }

        return toDoListOpt; // 삭제되었든 아니든, 원래의 값을 반환
    }
    public List<ToDoList> getTodayToDoList(int userId) {
        LocalDate date = LocalDate.now();
        Optional<Category> byDateAndUserId = categoryRepository.findByDateAndUserId(userId, date);

        if (byDateAndUserId.isPresent()) {
            Category category = byDateAndUserId.get();
            return toDoListRepository.findAllByUserIdAndCategoryId(userId, category.getPid());
        } else {
            // 카테고리가 없을 경우 빈 리스트 반환
            return Collections.emptyList();
        }
    }

}
