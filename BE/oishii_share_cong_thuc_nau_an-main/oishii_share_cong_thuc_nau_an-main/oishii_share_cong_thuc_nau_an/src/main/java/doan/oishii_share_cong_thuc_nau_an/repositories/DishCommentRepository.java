package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.entities.DishComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishCommentRepository extends JpaRepository<DishComment, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo" +
            "(dc.dishCommentID,dc.content,dc.flag,dc.startRate,dc.totalLike," +
            "dc.totalDisLike,cast(dc.createDate as string ),cast(dc.updateDate as string ),a.userName, a.avatarImage)" +
            " from DishComment dc join dc.account a where dc.status <> 3 and dc.dishID.dishID = :dishId and a.status <> 3 order by dc.dishCommentID desc")
    public Page<DishCommentAccountVo> findDishCommentByDishId(Integer dishId, Pageable page);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo" +
            "(dc.dishCommentID,dc.content,dc.flag,dc.startRate,dc.totalLike," +
            "dc.totalDisLike,cast(dc.createDate as string ),cast(dc.updateDate as string ),a.userName, a.avatarImage)" +
            " from DishComment dc join dc.account a where dc.status <> 3 and dc.dishID.dishID = :dishId and a.status <> 3 order by dc.dishCommentID desc")
    public List<DishCommentAccountVo> findDishCommentByDishId(Integer dishId);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo" +
            "(dc.dishCommentID,dc.content,dc.flag,dc.startRate,dc.totalLike," +
            "dc.totalDisLike,cast(dc.createDate as string ),cast(dc.updateDate as string ),a.userName, a.avatarImage)" +
            " from DishComment dc join dc.account a where dc.status = 2  and a.status <> 3 and" +
            "(cast(dc.dishCommentID as string ) like :searchData or dc.content like :searchData" +
            " or a.userName like :searchData ) order by dc.dishCommentID asc")
    public Page<DishCommentAccountVo> findReportDishComment(String searchData, Pageable pageable);

    @Query(value = "SELECT SUM(dc.start_rate) as a , COUNT(dc.dish_id) FROM dbo.dish_comment dc WHERE dc.dish_id = :dishId",nativeQuery = true)
    List<Object[]> getStarRate(@Param("dishId") int dishId);
}
