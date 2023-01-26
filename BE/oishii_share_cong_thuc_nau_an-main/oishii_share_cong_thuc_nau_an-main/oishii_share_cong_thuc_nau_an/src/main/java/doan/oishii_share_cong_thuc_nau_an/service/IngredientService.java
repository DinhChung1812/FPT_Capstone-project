//package doan.oishii_share_cong_thuc_nau_an.service;
//
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientCategoryVo;
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientUnitVo;
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientVo;
//import doan.oishii_share_cong_thuc_nau_an.common.vo.SaveIngredientRequest;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.Ingredient;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//public interface IngredientService {
//
//    Page<IngredientVo> findAllIngredient(String searchData,Integer pageIndex, Integer pageSize );
//
//    Ingredient save(SaveIngredientRequest saveIngredientRequest);
//
//    IngredientVo getIngredientDetail(Integer ingredientID);
//
//    Boolean deleteIngredient(Integer ingredientID);
//
//    List<IngredientCategoryVo> getListIngredientCategory();
//
//    List<IngredientUnitVo> getListIngredientUnit();
//
//
//}
