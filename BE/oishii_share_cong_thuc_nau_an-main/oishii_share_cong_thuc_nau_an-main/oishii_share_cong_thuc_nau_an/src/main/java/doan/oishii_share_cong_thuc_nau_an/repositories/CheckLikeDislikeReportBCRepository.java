package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo;
import doan.oishii_share_cong_thuc_nau_an.entities.CheckLikeDislikeReportBC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CheckLikeDislikeReportBCRepository extends JpaRepository<CheckLikeDislikeReportBC, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo(" +
            " c.checkLike, c.checkDislike, c.checkReport)" +
            " from CheckLikeDislikeReportBC c where c.account.accountId =:accountId and c.blogComment.blogCommentID =:blogCommentId")
    public CheckLikeDislikeReportVo getCheckLikeDislikeReportBC(Integer blogCommentId, Integer accountId);

    @Modifying
    @Query( "update CheckLikeDislikeReportBC c set c.checkReport =1 where c.account.accountId =:accountId and c.blogComment.blogCommentID =:commentId")
    int updateCheckReportBC(Integer accountId, Integer commentId);
}
