package doan.oishii_share_cong_thuc_nau_an.repositories;


import doan.oishii_share_cong_thuc_nau_an.common.vo.StepVo;
import doan.oishii_share_cong_thuc_nau_an.entities.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface StepRepository extends JpaRepository<Step, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.StepVo" +
            "(s.stepID , s.describe , s.title) from Step s where s.formulaID.formulaID = :formulaId")
    public List<StepVo> findByFormulaID(Integer formulaId);

    public void deleteByStepIDAndTitle(Integer stepId, Integer title);

    public Step findByStepIDAndTitle(Integer stepId, Integer title);

    @Modifying
    @Transactional
    @Query(value = "DELETE s FROM dbo.step s JOIN dbo.formula f ON f.formula_id = s.formula_id\n" +
            "JOIN dbo.dish d ON d.formula_id = f.formula_id WHERE d.dish_id= :dishId",nativeQuery = true)
    public void deleteStepByDishId(@Param("dishId") Integer dishId);
}
