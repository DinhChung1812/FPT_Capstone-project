package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import lombok.Data;

import java.util.List;

@Data
public class IngredientDetailResponse {
    private Integer ingredientDetailID;
    private String name;
    private Integer quantity;
    private String unit;
    private Integer calo;
    private Integer mainIngredient;
    private List<IngredientChangeResponse> listIngredientChange;
}
