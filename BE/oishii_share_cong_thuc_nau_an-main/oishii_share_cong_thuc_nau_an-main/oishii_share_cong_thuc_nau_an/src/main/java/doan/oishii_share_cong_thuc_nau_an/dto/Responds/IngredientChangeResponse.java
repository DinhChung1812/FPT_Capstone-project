package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import lombok.Data;

@Data
public class IngredientChangeResponse {
    private Integer ingredientChangeID;
    private String name;
    private Integer quantity;
    private String unit;
    private Integer calo;
}
