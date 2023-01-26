package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Integer  statusCode;
    private String message;
}
