//package doan.oishii_share_cong_thuc_nau_an.repositories;
//
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientCategoryVo;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.Ingredient;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientCategory;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Integer> {
//
//    IngredientCategory findByIngredientCateID(Integer id);
//
//    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientCategoryVo(" +
//            "ic.ingredientCateID, ic.name) from IngredientCategory ic")
//    List<IngredientCategoryVo> getListIngredientCategory();
//}
