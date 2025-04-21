package to_do_list_API.TDL_API.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "to_do_list")
public class ToDoList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String task;
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isChecked;

    // 연관 관계 없이 userId와 categoryId  저장
    private int userId;
    private int categoryId;
}
