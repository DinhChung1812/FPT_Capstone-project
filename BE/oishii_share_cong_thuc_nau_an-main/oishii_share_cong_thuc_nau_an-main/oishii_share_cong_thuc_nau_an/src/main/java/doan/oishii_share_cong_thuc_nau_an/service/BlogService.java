package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogVo;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BlogService {

    public Page<BlogVo> getListBlogActive (String searchData, Integer pageIndex, Integer pageSize);

    public Page<BlogVo> getListBlogPending(String searchData,Integer pageIndex, Integer pageSize);

    public BlogVo getBlogDetail (Integer blogId);

    ResponseEntity<?> createBlog(String title, String content, Account account);

    ResponseEntity<?> updateBlog(Integer blogId,String title,String content, Account account);

    ResponseEntity<?> deleteBlog(Integer blogId,  Account account);

    ResponseEntity<?> approveBlog(Integer blogId);

    ResponseEntity<?> likeBlog(Integer blogId, Account account);

    ResponseEntity<?> dislikeBlog(Integer blogId, Account account);
}
