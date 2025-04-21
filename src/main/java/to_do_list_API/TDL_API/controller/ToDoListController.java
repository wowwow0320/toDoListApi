package to_do_list_API.TDL_API.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import to_do_list_API.TDL_API.SessionConst;
import to_do_list_API.TDL_API.domain.ToDoList;
import to_do_list_API.TDL_API.domain.User;
import to_do_list_API.TDL_API.service.ToDoListService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ToDoListController {

    private final ToDoListService toDoListService;

    @PostMapping("/toDoList/{category}")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoList toDoList,
                                           @PathVariable int category,
                                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        int userId = user.getPid();
        ToDoList saveToDo= toDoListService.saveToDo(toDoList, userId, category);

        return ResponseEntity.ok(saveToDo);
    }
    @GetMapping("/toDoList/{category}")
    public ResponseEntity<?>  getToDo(@PathVariable int category,
                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user){
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        int userId = user.getPid();
        List<ToDoList> toDoList = toDoListService.getToDoList(userId, category);

        if (toDoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 카테고리에 할 일이 존재하지 않습니다.");
        }

        return ResponseEntity.ok(toDoList);
    }
}
