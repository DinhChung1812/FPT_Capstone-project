package doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish;

import doan.oishii_share_cong_thuc_nau_an.entities.Dish;
import doan.oishii_share_cong_thuc_nau_an.entities.Step;
import lombok.Data;

import java.util.Set;

@Data
public class FomulaRequest {
    private int fomulaId;
    private String describe;
    private String summary;
    private String varifier;
    private Set<Step> listStep;
    private Dish dish;
}
