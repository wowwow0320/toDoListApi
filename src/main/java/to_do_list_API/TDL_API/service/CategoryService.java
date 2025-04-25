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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Category saveOrUpdateCategory(CategoryDto categoryDto, int userId) {
        LocalDate date = categoryDto.getDate();

        // 로그 찍어서 실제 값 확인
        System.out.println("🔍 Checking for userId: " + userId + ", date: " + date);

        Optional<Category> existingCategoryOpt = categoryRepository.findByUserIdAndDate(userId, date);
        Category category;

        if (existingCategoryOpt.isPresent()) {
            category = existingCategoryOpt.get();

            if (categoryDto.getCategory1() != null && category.getCategory1() == null) {
                category.setCategory1(categoryDto.getCategory1());
            }
            if (categoryDto.getCategory2() != null && category.getCategory2() == null) {
                category.setCategory2(categoryDto.getCategory2());
            }
            if (categoryDto.getCategory3() != null && category.getCategory3() == null) {
                category.setCategory3(categoryDto.getCategory3());
            }

        } else {
            // 중복 방지 더블 체크 (예외 상황 대비)
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
        }

        return categoryRepository.save(category);

    }

    public Optional<Category> getCategory(LocalDate date, int userId) {
        return categoryRepository.findByUserIdAndDate(userId, date);
    }
}
