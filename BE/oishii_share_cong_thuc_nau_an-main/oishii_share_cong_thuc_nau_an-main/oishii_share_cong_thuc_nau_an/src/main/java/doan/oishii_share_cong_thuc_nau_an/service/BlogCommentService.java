package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BlogCommentService {
    public Page<BlogCommentAccountVo> findBlogCommentByBlogId(Integer blogId, Integer pageIndex, Integer pageSize);

    ResponseEntity<?> createComment(Integer blogId, String content,  Account account);

    ResponseEntity<?> updateComment(Integer blogId, Integer blogCommentId, String content,  Account account);

    ResponseEntity<?>  reportComment(Integer blogCommentId , Account account);

    Page<BlogCommentAccountVo> likeBlogComment(Integer blogCommentId, Account account, Integer pageIndex);

    Page<BlogCommentAccountVo> dislikeBlogComment(Integer blogCommentId, Account account,Integer pageIndex);

    ResponseEntity<?> deleteBlogComment(Integer blogCommentId,  Account account);

    Page<BlogCommentAccountVo> findReportBlogComment(String searchData,Integer pageIndex, Integer pageSize);

    ResponseEntity<?> approveComment( Integer blogCommentId);
}
