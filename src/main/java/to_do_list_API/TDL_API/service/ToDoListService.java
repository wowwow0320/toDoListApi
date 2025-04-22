package to_do_list_API.TDL_API.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import to_do_list_API.TDL_API.domain.Category;
import to_do_list_API.TDL_API.domain.ToDoList;
import to_do_list_API.TDL_API.dto.ToDoCountPerDateDTO;
import to_do_list_API.TDL_API.repository.CategoryRepository;
import to_do_list_API.TDL_API.repository.ToDoListRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoListService {
    final private CategoryRepository categoryRepository;
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
    public List<ToDoList> getTodayToDoList(int userId, int categoryId){

        return toDoListRepository.findAllByUserIdAndCategoryId(userId, categoryId);
    }
    public List<ToDoCountPerDateDTO> countToDoListGroupByDate(int userId) {

        // 1. 해당 유저의 모든 카테고리 가져오기
        List<Category> allCategories = categoryRepository.findAllByPid(userId);

        // 2. 날짜별로 그룹핑
        Map<LocalDate, List<Category>> groupedByDate = allCategories.stream()
                .collect(Collectors.groupingBy(Category::getDate));

        // 3. 날짜별로 categoryId 모아서 ToDoList 개수 세기
        List<ToDoCountPerDateDTO> result = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Category>> entry : groupedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Integer> categoryIds = entry.getValue().stream()
                    .map(Category::getPid)
                    .collect(Collectors.toList());

            int count = toDoListRepository.countByUserIdAndCategoryIdIn(userId, categoryIds);

            result.add(new ToDoCountPerDateDTO(date, count));
        }

        return result;
    }




}
