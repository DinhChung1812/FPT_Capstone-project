package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo;
import doan.oishii_share_cong_thuc_nau_an.entities.CheckLikeDislikeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckLikeDislikeReportRepository extends JpaRepository<CheckLikeDislikeReport, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo(" +
            "c.account.accountId , c.dishComment.dishCommentID, c.checkLike, c.checkDislike, c.checkReport)" +
            " from CheckLikeDislikeReport c where c.account.accountId =:accountId and c.dishComment.dishCommentID =:commentId")
    public CheckLikeDislikeReportVo getCheckLikeDislikeReport(Integer accountId, Integer commentId);


    @Modifying
    @Query( "update CheckLikeDislikeReport c set c.checkReport =1 where c.account.accountId =:accountId and c.dishComment.dishCommentID =:commentId")
    int updateCheckReport(Integer accountId, Integer commentId);

//    @Modifying
//    @Query( "update CheckLikeDislikeReport c set c.checkLike =1, c.checkDislike =0 where c.account.accountId =:accountId and c.dishComment.dishCommentID =:commentId")
//    int updateCheckLike1(Integer accountId, Integer commentId);
//
//    @Modifying
//    @Query( "update CheckLikeDislikeReport c set c.checkLike =1, c.checkDislike =0 where c.account.accountId =:accountId and c.dishComment.dishCommentID =:commentId")
//    int updateCheckLike1(Integer accountId, Integer commentId);

}
