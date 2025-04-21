package to_do_list_API.TDL_API.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String category1;
    private String category2;
    private String category3;
    private LocalDate date;

    // 연관 관계 없이 userId만 저장
    private int userId;

}
