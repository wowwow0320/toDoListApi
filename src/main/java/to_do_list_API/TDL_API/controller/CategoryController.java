package to_do_list_API.TDL_API.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import to_do_list_API.TDL_API.SessionConst;
import to_do_list_API.TDL_API.domain.Category;
import to_do_list_API.TDL_API.domain.User;
import to_do_list_API.TDL_API.dto.CategoryDto;
import to_do_list_API.TDL_API.service.CategoryService;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/home")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto,
                                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        int userId = user.getPid();
        Category savedCategory = categoryService.saveOrUpdateCategory(categoryDto, userId);
        return ResponseEntity.ok(savedCategory);
    }
    @GetMapping("/home")
    public ResponseEntity<?> getCategory(@RequestParam(value = "date", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                         @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        if (date == null) {
            date = LocalDate.now();  // 기본값: 오늘 날짜
        }

        int userId = user.getPid();
        Optional<Category> category = categoryService.getCategory(date, userId);

        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 날짜에 대한 카테고리를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(category);
    }



}
