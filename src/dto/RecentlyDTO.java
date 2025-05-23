package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentlyDTO {

    private int viewedIdx;           // 최근 본 기록 ID (PK)
    private int memberIdx;           // 회원 ID (FK)
    private int phoneIdx;            // 휴대폰 ID (FK)


}
