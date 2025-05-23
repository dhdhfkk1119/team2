package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private int cartIdx;
    private int memberIdx;
    private int phoneIdx;
    private int quantity;
    private LocalDate insertAt;



}
