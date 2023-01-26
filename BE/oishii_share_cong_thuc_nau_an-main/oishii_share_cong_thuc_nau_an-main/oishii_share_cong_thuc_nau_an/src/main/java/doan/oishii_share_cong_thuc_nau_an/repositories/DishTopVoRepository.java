package doan.oishii_share_cong_thuc_nau_an.repositories;


import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishTopVoRepository extends JpaRepository<DishVo, Integer> {

    @Query(name = "DishVoQuery", nativeQuery = true)
    List<DishVo> getTop5Dish(Integer numberDay);

//    @Query(name = "FavoriteDishQuery", nativeQuery = true)
//    Page<DishVo> getFavoriteRecipe(Integer accountId,String searchData, Pageable pageable);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo(" +
            "d.dishID, d.level, d.calo, d.name, d.numberPeopleForDish," +
            "f.formulaID, f.describe, f.summary, cast(d.createDate as string )" +
            ", d.time, a.userName, a.avatarImage) from FavouriteRecipe fr join fr.formula f join " +
            "f.dish d join f.account a where d.status <> 3 and " +
            "a.status <> 3 and fr.account.accountId = :accountId  and d.name like :searchData order by d.createDate desc ,d.dishID desc ")
    Page<DishVo> getFavoriteRecipe(Integer accountId,String searchData, Pageable pageable);

}
