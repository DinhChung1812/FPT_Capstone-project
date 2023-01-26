package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo;
import doan.oishii_share_cong_thuc_nau_an.entities.DishImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DishImageRepository extends JpaRepository<DishImage, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo(" +
            "di.dishImageID, di.url, di.note) from DishImage di where di.dishID.dishID = :dishId")
    public List<DishImageVo> findByDishID(Integer dishId);

    @Modifying
    @Transactional
    @Query(value = "DELETE di FROM dbo.dish_image di JOIN dbo.dish d ON d.dish_id = di.dish_id WHERE d.dish_id=:dishId",nativeQuery = true)
    public void deleteDishImageByDishId(@Param("dishId") Integer dishId);

}
