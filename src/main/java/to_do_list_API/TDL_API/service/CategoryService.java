package to_do_list_API.TDL_API.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import to_do_list_API.TDL_API.domain.Category;
import to_do_list_API.TDL_API.dto.CategoryDto;
import to_do_list_API.TDL_API.repository.CategoryRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveOrUpdateCategory(CategoryDto categoryDto, int userId) {
        LocalDate date = categoryDto.getDate();
        System.out.println("🔍 Checking for userId: " + userId + ", date: " + date);

        Optional<Category> existingCategoryOpt = categoryRepository.findByUserIdAndDate(userId, date);
        Category category;

        if (existingCategoryOpt.isPresent()) {
            category = existingCategoryOpt.get();
            boolean changed = false;

            if (categoryDto.getCategory1() != null && category.getCategory1() == null) {
                category.setCategory1(categoryDto.getCategory1());
                changed = true;
            }
            if (categoryDto.getCategory2() != null && category.getCategory2() == null) {
                category.setCategory2(categoryDto.getCategory2());
                changed = true;
            }
            if (categoryDto.getCategory3() != null && category.getCategory3() == null) {
                category.setCategory3(categoryDto.getCategory3());
                changed = true;
            }

            // 실제로 바뀐 필드가 있을 경우만 save
            if (changed) {
                return categoryRepository.save(category);
            } else {
                return category;  // 바뀐 내용 없으면 그냥 기존 값 반환
            }

        } else {
            // 중복 체크 (예외적 상황 방지)
            if (categoryRepository.existsByUserIdAndDate(userId, date)) {
                throw new IllegalStateException("이미 해당 날짜의 카테고리가 존재합니다.");
            }

            category = Category.builder()
                    .category1(categoryDto.getCategory1())
                    .category2(categoryDto.getCategory2())
                    .category3(categoryDto.getCategory3())
                    .date(date)
                    .userId(userId)
                    .build();

            return categoryRepository.save(category);
        }
    }



    public Optional<Category> getCategory(LocalDate date, int userId) {
        return categoryRepository.findByUserIdAndDate(userId, date);
    }


}
