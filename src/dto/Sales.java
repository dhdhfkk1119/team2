package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sales {

    private int saleIdx;
    private int memberIdx;
    private int phoneIdx;
    private LocalDate salesAt;

}
