package to_do_list_API.TDL_API.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Data
public class ToDoSummaryDto {
    private LocalDate date;
    private double successRate;
    private int checkedCount;
    private int totalCount;

    public ToDoSummaryDto(LocalDate date, double successRate, int checkedCount, int totalCount) {
        this.date = date;
        this.successRate = successRate;
        this.checkedCount = checkedCount;
        this.totalCount = totalCount;
    }

}

