package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    private int phoneId;
    private String phoneName;
    private LocalDateTime createdAt;
    private int price;
    private String phoneState;
    private int quantity;
    private int salesCount;
}
