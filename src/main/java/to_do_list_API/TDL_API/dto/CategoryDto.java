package to_do_list_API.TDL_API.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
public class CategoryDto {
    private String category1;
    private String category2;
    private String category3;
    private LocalDate date;
    private String userId;

}
