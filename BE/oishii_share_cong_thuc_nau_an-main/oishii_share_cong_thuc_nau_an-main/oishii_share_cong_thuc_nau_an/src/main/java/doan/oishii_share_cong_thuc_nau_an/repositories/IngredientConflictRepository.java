package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientConflictVo;
import doan.oishii_share_cong_thuc_nau_an.entities.IngredientConflict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientConflictRepository extends JpaRepository<IngredientConflict, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientConflictVo(" +
            "ic.ingredientConflictId, ic.ingredientA, ic.ingredientB , ic.consequence, ic.createUsername, ic.createDate, ic.updateUsername, ic.updateDate) " +
            " from IngredientConflict ic where ic.ingredientA like :searchData or ic.ingredientB like :searchData  order by ic.ingredientA" )
    public Page<IngredientConflictVo> getListIngredientConflict (String searchData, Pageable pageable);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientConflictVo(" +
            "ic.ingredientConflictId, ic.ingredientA, ic.ingredientB , ic.consequence, ic.createUsername, ic.createDate, ic.updateUsername, ic.updateDate) " +
            " from IngredientConflict ic  where ic.ingredientConflictId = :ingredientConflictId" )
    public IngredientConflictVo getIngredientConflictInfo (Integer ingredientConflictId);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientConflictVo(" +
            "ic.ingredientConflictId, ic.ingredientA, ic.ingredientB , ic.consequence, ic.createUsername, ic.createDate, ic.updateUsername, ic.updateDate) " +
            " from IngredientConflict ic where (ic.ingredientA = :ingredientA and ic.ingredientB = :ingredientB) or " +
            "(ic.ingredientB = :ingredientA and ic.ingredientA = :ingredientB)" )
    public List<IngredientConflictVo> checkIngredientConflictExist (String ingredientA, String ingredientB);


}
