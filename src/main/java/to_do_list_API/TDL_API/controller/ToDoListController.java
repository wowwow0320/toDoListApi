package to_do_list_API.TDL_API.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import to_do_list_API.TDL_API.SessionConst;
import to_do_list_API.TDL_API.domain.Category;
import to_do_list_API.TDL_API.domain.ToDoList;
import to_do_list_API.TDL_API.domain.User;
import to_do_list_API.TDL_API.service.ToDoListService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ToDoListController {

    private final ToDoListService toDoListService;

    @PostMapping("/toDoList/{categoryPid}/{categoryName}")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoList toDoList,
                                           @PathVariable int categoryPid, @PathVariable String categoryName,
                                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        int userId = user.getPid();
        ToDoList saveToDo= toDoListService.saveToDo(toDoList, userId, categoryPid, categoryName);

        return ResponseEntity.ok(saveToDo);
    }
    @GetMapping("/toDoList/{categoryPid}/{categoryName}")
    public ResponseEntity<?>  getToDo(@PathVariable int categoryPid,@PathVariable String categoryName,
                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user){
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        int userId = user.getPid();
        List<ToDoList> toDoList = toDoListService.getToDoList(userId, categoryPid, categoryName);

        if (toDoList.isEmpty()) {
            ToDoList emptyToDoList = ToDoList.builder()
                    .task(null)
                    .isChecked(false)
                    .categoryId(categoryPid)
                    .userId(userId)
                    .categoryName(categoryName)
                    .build();
            return ResponseEntity.ok(emptyToDoList);
        }

        return ResponseEntity.ok(toDoList);
    }
    @PostMapping("/toDoList/check/{todolistPid}")
    public ResponseEntity<String> updateToDoListCheckedStatus(@PathVariable int todolistPid,
                                                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Optional<ToDoList> updatedToDo = toDoListService.updateToDoListCheckedStatus(todolistPid);

        if (updatedToDo.isPresent()) {
            return ResponseEntity.ok("할 일 완료 상태로 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 pid에 대한 할 일을 찾을 수 없습니다.");
        }
    }
    @PostMapping("/toDoList/delete/{todolistPid}")
    public ResponseEntity<String> deleteToDoList(@PathVariable int todolistPid){
        Optional<ToDoList> deletedToDo = toDoListService.deleteToDoList(todolistPid);
        if(deletedToDo.isPresent()){
            return ResponseEntity.ok("할 일이 성공적으로 삭제되었습니다.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 pid에 대한 할 일을 찾을 수 없습니다.");
        }
    }
    @GetMapping("/toDoList")
    public ResponseEntity<?> getTodayToDoList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user){
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        int userId = user.getPid();
        List<ToDoList> todayToDoList = toDoListService.getTodayToDoList(userId);
        if (todayToDoList.isEmpty()) {
            ToDoList emptyToDoList = ToDoList.builder()
                    .task(null)
                    .isChecked(false)
                    .userId(userId)
                    .build();
            return ResponseEntity.ok(emptyToDoList);

        }

        return ResponseEntity.ok(todayToDoList);
    }
}
