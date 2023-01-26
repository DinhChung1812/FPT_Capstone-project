package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class SaveIngredientRequest {
    private Integer ingredientID;

    private String name;

    private Integer calo;

    private Double ingredientPrice;

    private Integer ingredientCategoryID;

    private Integer ingredientUnitID;
}
