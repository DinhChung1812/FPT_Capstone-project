package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.entities.DishComment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface DishCommentService {
     Page<DishCommentAccountVo> findDishCommentByDishId(Integer dishId,Integer pageIndex, Integer pageSize);

     Page<DishCommentAccountVo> findReportDishComment(String searchData,Integer pageIndex, Integer pageSize);

     ResponseEntity<?> createComment(Integer dishId,
                             String content, Integer starRate, Account account);

     ResponseEntity<?> updateComment(Integer dishId, Integer dishCommentId,
                                     String content, Integer starRate, Account account);

     DishComment reportComment(Integer dishCommentId , Account account, CheckLikeDislikeReportVo checkReport);

     DishComment likeComment(Integer dishCommentId , Account account);

     DishComment dislikeComment(Integer dishCommentId , Account account);

     DishComment deleteComment(Integer dishCommentId , Account account);


     ResponseEntity<?> approveComment( Integer dishCommentId);
}
