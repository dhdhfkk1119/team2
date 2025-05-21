package dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private int memberIdx;
    private String userName;
    private String userId;
    private String password;
    private LocalDate memberAt;

}
