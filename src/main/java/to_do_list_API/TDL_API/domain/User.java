package to_do_list_API.TDL_API.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String email;
    private String pw;
    private String nickname;

}
