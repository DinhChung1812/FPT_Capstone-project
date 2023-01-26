package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientChangeVo;
import doan.oishii_share_cong_thuc_nau_an.entities.IngredientChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IngredientChangeRepository extends JpaRepository<IngredientChange, Integer> {

//    @Modifying
//    @Query( "delete IngredientChange ic where ic.ingredientID.ingredientID = :id")
//    int deleteByIngredientID(Integer id);
    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientChangeVo(" +
            "ic.ingredientChangeID, ic.quantity, ic.unit," +
            " ic.name,  ic.calo) " +
            " from IngredientChange ic join ic.ingredientDetail id " +
            "where id.ingredientDetailID = :ingredientDetailId ")
    List<IngredientChangeVo> getIngredientChange(Integer ingredientDetailId);

    @Modifying
    @Transactional
    @Query(value = "DELETE ic FROM dbo.ingredient_change ic INNER JOIN dbo.ingredient_detail id ON id.ingredient_detail_id = ic.ingredient_detail_id\n" +
            "JOIN dbo.dish d ON d.dish_id = id.dish_id WHERE d.dish_id=:dishId",nativeQuery = true)
    void deleteAllIngredientChangeByDishId(@Param("dishId")Integer dishId);
}
