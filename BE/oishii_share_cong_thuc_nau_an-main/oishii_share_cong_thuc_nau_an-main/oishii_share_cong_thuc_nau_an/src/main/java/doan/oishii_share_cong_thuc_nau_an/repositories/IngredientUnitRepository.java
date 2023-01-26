//package doan.oishii_share_cong_thuc_nau_an.repositories;
//
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientCategoryVo;
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientUnitVo;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientCategory;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientUnit;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface IngredientUnitRepository extends JpaRepository<IngredientUnit, Integer> {
//
//    IngredientUnit findByIngredientUnitID(Integer id);
//
//    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientUnitVo(" +
//            "ic.ingredientUnitID, ic.name) from IngredientUnit ic")
//    List<IngredientUnitVo> getListIngredientUnit();
//}
