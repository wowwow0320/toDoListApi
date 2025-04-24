package to_do_list_API.TDL_API.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

        Optional<Category> existingCategoryOpt = categoryRepository.findByDateAndUserId(userId, date);
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
        return categoryRepository.findByDateAndUserId(userId, date);
    }
}
