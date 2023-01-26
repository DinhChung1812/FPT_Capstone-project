package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientChangeVo {
    //IngredientChange------------

    private Integer ingredientChangeId;

    private Integer quantity;

    private String unit;

    private String name;


    private Integer calo;

    private Integer ingredientDetailId;



    public IngredientChangeVo(Integer ingredientChangeId, Integer quantity, String unit, String name, Integer calo) {
        this.ingredientChangeId = ingredientChangeId;
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
        this.calo = calo;
    }
}
