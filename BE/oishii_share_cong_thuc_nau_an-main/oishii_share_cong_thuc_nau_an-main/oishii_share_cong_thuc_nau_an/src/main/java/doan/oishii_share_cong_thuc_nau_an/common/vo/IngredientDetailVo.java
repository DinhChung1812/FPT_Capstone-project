package doan.oishii_share_cong_thuc_nau_an.common.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDetailVo {

    //IngredientDetail------------

    private Integer ingredientDetailId;

    private Integer quantity;

    private String unit;

    private String name;


    private Integer calo;

    private Integer mainIngredient;

    private List<IngredientChangeVo> ingredientChangeVoList;


    //Ingredient-------------------

//    private Integer ingredientID;
//
//    private String ingredientName;
//
//    private Integer ingredientCalo;
//
//    private Double ingredientPrice;
//
//    //IngredientUnit--------------
//
//    private Integer ingredientUnitID;
//
//    private String unitName;


    public IngredientDetailVo(Integer ingredientDetailId, Integer quantity, String unit, String name, Integer calo, Integer mainIngredient) {
        this.ingredientDetailId = ingredientDetailId;
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
        this.calo = calo;
        this.mainIngredient = mainIngredient;
    }
}
