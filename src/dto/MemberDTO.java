package dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private int memberIdx; // 자동으로 값이 증가
    private String userName; // 유저 이름
    private String userId; // 유저 아이디
    private String password; // 유저 비밀번호
    private LocalDate memberAt; // 만든 시간

}
