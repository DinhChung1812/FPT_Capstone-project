package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientDetailVo;

import java.util.List;

public interface IngredientDetailService {

    List<IngredientDetailVo> findIngredientDetailVoByDishID (Integer dishId);

//    List<IngredientChangeVo> getIngredientChange (Integer ingredientId);
}
