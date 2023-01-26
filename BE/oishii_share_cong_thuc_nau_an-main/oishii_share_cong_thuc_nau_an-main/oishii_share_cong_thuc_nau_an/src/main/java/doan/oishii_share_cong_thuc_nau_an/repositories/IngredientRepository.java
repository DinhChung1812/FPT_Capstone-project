//package doan.oishii_share_cong_thuc_nau_an.repositories;
//
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientVo;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.Ingredient;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientDetail;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
//
//    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientVo(i.ingredientID, i.name, i.calo, i.ingredientPrice" +
//            ", iu.ingredientUnitID, iu.name," +
//            "ic.ingredientCateID, ic.name)" +
//            " from Ingredient i join i.ingredientUnitID iu join i.ingredientCategoryID ic" +
//            " where (cast(i.ingredientID as string ) like :searchData or i.name like :searchData) and i.deleteFlag <> 1")
//    public Page<IngredientVo> findAll(String searchData, Pageable page);
//
//    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientVo(i.ingredientID, i.name, i.calo, i.ingredientPrice" +
//            ", iu.ingredientUnitID, iu.name," +
//            "ic.ingredientCateID, ic.name)" +
//            " from Ingredient i join i.ingredientUnitID iu join i.ingredientCategoryID ic where i.ingredientID = :id and i.deleteFlag <>1")
//    public IngredientVo findByIngredientID(Integer id);
//
//    @Modifying
//    @Query( "update Ingredient i set i.deleteFlag =1 where i.ingredientID = :id")
//    int updateDeleteFlag(Integer id);
//
////    @Modifying
////    @Query( "delete Ingredient i where i.ingredientID = :id")
////    int deleteByIngredientID(Integer id);
//}
