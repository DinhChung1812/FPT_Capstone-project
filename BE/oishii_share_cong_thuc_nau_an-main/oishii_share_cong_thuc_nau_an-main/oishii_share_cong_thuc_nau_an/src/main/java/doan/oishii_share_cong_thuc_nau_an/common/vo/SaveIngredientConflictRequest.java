package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;

@Data
public class SaveIngredientConflictRequest {
    private Integer ingredientConflictId;

    private String ingredientA;

    private String ingredientB;

    private String consequence;
}
