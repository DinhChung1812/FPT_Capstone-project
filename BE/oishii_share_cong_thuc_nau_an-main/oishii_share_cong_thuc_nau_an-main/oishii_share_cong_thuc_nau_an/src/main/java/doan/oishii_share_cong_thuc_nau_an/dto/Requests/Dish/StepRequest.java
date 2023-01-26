package doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish;

import doan.oishii_share_cong_thuc_nau_an.entities.Formula;
import lombok.Data;

@Data
public class StepRequest {
    private Integer stepID;
    private String describe;
    private Formula formulaID;
}
