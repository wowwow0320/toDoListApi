package to_do_list_API.TDL_API.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
public class ToDoCountPerDateDTO {
    private LocalDate date;
    private long count;

    public ToDoCountPerDateDTO(LocalDate date, long count) {
        this.date = date;
        this.count = count;
    }

    // getters and setters 생략 가능 (Lombok 쓰면 @Data 등으로 가능)
}
