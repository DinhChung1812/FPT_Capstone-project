package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo;
import doan.oishii_share_cong_thuc_nau_an.entities.DishCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface CategoryRepository extends JpaRepository<DishCategory, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo" +
            "(c.dishCategoryID, c.name, c.dishCategoryImage) from DishCategory c where c.status <> 3")
    List<DishCategoryVo> findAllCategory();

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo" +
            "(c.dishCategoryID, c.name, c.dishCategoryImage) from DishCategory c where c.status <> 3 " +
            "and ( cast(c.dishCategoryID as string ) like :searchData or c.name like :searchData ) order by c.dishCategoryID desc ")
    Page<DishCategoryVo> getListCategory(String searchData, Pageable pageable);
    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo" +
            "(c.dishCategoryID, c.name, c.dishCategoryImage) from DishCategory c where c.status <> 3 " +
            "and c.name like :name  order by c.dishCategoryID desc ")
    List<DishCategoryVo> getCategoryByName(String name);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo" +
            "(c.dishCategoryID, c.name, c.dishCategoryImage) from DishCategory c where c.status <> 3 " +
            "and c.dishCategoryID = :categoryId ")
    DishCategoryVo getCategoryInfo(Integer categoryId);

    @Modifying
    @Transactional
    @Query(value = "select ddc.dish_categoryid from dish_dish_category ddc join dish d on ddc.dish_id = d.dish_id" +
            " where d.dish_id <>3 and ddc.dish_categoryid = :categoryId ", nativeQuery = true)
    List<Integer> checkCategoryExists(Integer categoryId);

    @Modifying
    @Transactional
    @Query(value = "DELETE  ddc FROM dbo.dish_dish_category ddc WHERE ddc.dish_id=:dishId",nativeQuery = true)
    void deleteDishCategoryByDishId(@Param("dishId") Integer dishId);
}
