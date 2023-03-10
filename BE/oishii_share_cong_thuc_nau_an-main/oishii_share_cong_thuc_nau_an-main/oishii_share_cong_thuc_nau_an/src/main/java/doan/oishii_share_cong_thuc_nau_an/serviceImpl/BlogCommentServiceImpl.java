package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.entities.*;
import doan.oishii_share_cong_thuc_nau_an.repositories.BlogCommentRepository;
import doan.oishii_share_cong_thuc_nau_an.repositories.BlogRepository;
import doan.oishii_share_cong_thuc_nau_an.repositories.CheckLikeDislikeReportBCRepository;
import doan.oishii_share_cong_thuc_nau_an.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CheckLikeDislikeReportBCRepository checkLikeDislikeReportBCRepository;


    @Override
    public Page<BlogCommentAccountVo> findBlogCommentByBlogId(Integer blogId, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return blogCommentRepository.findBlogCommentByBlogId(blogId,pageable);
    }

    @Override
    public ResponseEntity<?> createComment(Integer blogId, String content, Account account) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NotFoundException(StatusCode.Not_Found,"blog " + blogId + " Not exist or blog was blocked "));
        BlogComment blogComment = new BlogComment();
        blogComment.setAccount(account);
        blogComment.setFlag(0);
        blogComment.setContent(content);
        blogComment.setBlogID(blog);
        blogComment.setCreateDate(LocalDate.now());
        blogComment.setUpdateDate(LocalDate.now());
        blogComment.setTotalDisLike(0);
        blogComment.setTotalLike(0);
        blogComment.setStatus(1); // status = 1 active
        blog.setNumberComment(blog.getNumberComment()+1);
        blogCommentRepository.save(blogComment);
        blogRepository.save(blog);
        return ResponseEntity.ok( new MessageVo("l??u b??nh lu???n th??nh c??ng", "success"));
    }

    @Override
    public ResponseEntity<?> updateComment(Integer blogId, Integer blogCommentId, String content, Account account) {
        BlogComment blogComment = blogCommentRepository.findById(blogCommentId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"blog comment" + blogCommentId + " Not exist or blog comment was blocked "));
        if(blogComment.getAccount().getAccountId() == account.getAccountId()){
            blogComment.setAccount(account);
            blogComment.setContent(content);
            blogComment.setUpdateDate(LocalDate.now());
            blogComment.setStatus(1);
            blogCommentRepository.save(blogComment);
            return ResponseEntity.ok( new MessageVo("c???p nh???t b??nh lu???n th??nh c??ng", "success"));
        } else{
            return ResponseEntity.ok( new MessageVo("B???n kh??ng c?? quy???n s???a b??nh lu???n n??y", "error"));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> reportComment(Integer blogCommentId, Account account) {
        CheckLikeDislikeReportVo checkReport = checkLikeDislikeReportBCRepository.getCheckLikeDislikeReportBC(blogCommentId,account.getAccountId());
        if(checkReport == null || checkReport.getCheckReport() == null || checkReport.getCheckReport() != 1){ //n???u ng ????ng nh???p ch??a report b??nh lu???n
            BlogComment blogComment = blogCommentRepository.findById(blogCommentId).orElseThrow(() ->
                    new NotFoundException(StatusCode.Not_Found,"blog comment" + blogCommentId + " Not exist or blog comment was blocked "));
            blogComment.setFlag(blogComment.getFlag()+1);
            if(blogComment.getFlag()>=3){ // n???u c?? 3 ng ????? l??n report
                blogComment.setStatus(2); //chuy???n tr???ng th??i sang danh s??ch b??o c??o cho admin
            }
            CheckLikeDislikeReportBC checkLikeDislikeReportBC = new CheckLikeDislikeReportBC();
            if(checkReport == null){
                checkLikeDislikeReportBC.setCheckReport(1);
                checkLikeDislikeReportBC.setCheckDislike(0);
                checkLikeDislikeReportBC.setCheckLike(0);
                checkLikeDislikeReportBC.setBlogComment(blogComment);
                checkLikeDislikeReportBC.setAccount(account);
                checkLikeDislikeReportBCRepository.save(checkLikeDislikeReportBC);
            }else {
                checkLikeDislikeReportBCRepository.updateCheckReportBC(account.getAccountId(), blogCommentId);
            }
            blogCommentRepository.save(blogComment);
            return ResponseEntity.ok(new MessageVo("???? b??o c??o b??nh lu???n cho qu???n tr??? vi??n","success"));
        }else {
            return ResponseEntity.ok(new MessageVo("B???n ???? b??o c??o b??nh lu???n n??y r???i","info"));
        }
    }

    @Override
    public Page<BlogCommentAccountVo> likeBlogComment(Integer blogCommentId, Account account,Integer pageIndex) {
        BlogComment blogComment = blogCommentRepository.findById(blogCommentId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"blog comment" + blogCommentId + " Not exist or blog comment was blocked "));
        CheckLikeDislikeReportVo checkLikeOrDislikes = checkLikeDislikeReportBCRepository.getCheckLikeDislikeReportBC(blogCommentId,account.getAccountId());
        CheckLikeDislikeReportBC checkLikeDislikeReportBC = new CheckLikeDislikeReportBC();
        checkLikeDislikeReportBC.setBlogComment(blogComment);
        checkLikeDislikeReportBC.setAccount(account);
        checkLikeDislikeReportBC.setCheckId(new CheckLikeDislikeReportId(account.getAccountId(), blogCommentId));
        if (checkLikeOrDislikes == null) { //ch??a like , dislike  bao gi???
            blogComment.setTotalLike(blogComment.getTotalLike() + 1); //t??ng total like l??n 1
            blogComment.setTotalDisLike(blogComment.getTotalDisLike());// gi??? nguy??n total dislike
            checkLikeDislikeReportBC.setCheckLike(1);//like
            checkLikeDislikeReportBC.setCheckDislike(0);//ko dislike
            checkLikeDislikeReportBC.setCheckReport(0);
        } else if (checkLikeOrDislikes.getCheckLike() == null || checkLikeOrDislikes.getCheckLike() != 1) { //ng?????i ????ng nh???p ch??a like, ???? dislike
            blogComment.setTotalLike(blogComment.getTotalLike() + 1); //t??ng total like l??n 1
            if (checkLikeOrDislikes.getCheckDislike() == 1 && blogComment.getTotalDisLike() > 0) { //n???u ??ang dislike
                blogComment.setTotalDisLike(blogComment.getTotalDisLike() - 1); //gi???m total dislike ??i 1
            }
            checkLikeDislikeReportBC.setCheckLike(1); //like
            checkLikeDislikeReportBC.setCheckDislike(0); //b??? dislike
            checkLikeDislikeReportBC.setCheckReport(checkLikeOrDislikes.getCheckReport());// gi??? nguy??n report
        } else { // ng?????i ????ng nh???p ???? like => b???m n??t ????? b??? like
            if (blogComment.getTotalLike() > 0) {
                blogComment.setTotalLike(blogComment.getTotalLike() - 1); // gi???m total like xu???ng 1
            }
            blogComment.setTotalDisLike(blogComment.getTotalDisLike());// gi??? nguy??n total dislike
            checkLikeDislikeReportBC.setCheckLike(0); //b??? like
            checkLikeDislikeReportBC.setCheckDislike(checkLikeOrDislikes.getCheckDislike()); // gi??? nguy??n dislike
            checkLikeDislikeReportBC.setCheckReport(checkLikeOrDislikes.getCheckReport());// gi??? nguy??n report
        }
        checkLikeDislikeReportBCRepository.save(checkLikeDislikeReportBC);
        blogCommentRepository.save(blogComment);
        Page<BlogCommentAccountVo> blogCommentAccountVos = findBlogCommentByBlogId(blogRepository.getBlogIdByBlogCommentId(blogCommentId), pageIndex-1,5);
        for (BlogCommentAccountVo blogCommentAccountVo : blogCommentAccountVos) {
                blogCommentAccountVo.setCheckLike(checkLikeDislikeReportBC.getCheckLike());
                blogCommentAccountVo.setCheckDislike(checkLikeDislikeReportBC.getCheckDislike());

            if (blogCommentAccountVo.getAccountUserName().equals(account.getUserName())) {// n???u ng?????i t???o comment l?? ng?????i ????ng nh???p
                blogCommentAccountVo.setCheckEdit(1);// ???????c quy???n edit, delete
            } else {
                blogCommentAccountVo.setCheckEdit(0);// ko dc quy???n edit, delete
            }
        }
        return blogCommentAccountVos;
    }

    @Override
    public Page<BlogCommentAccountVo> dislikeBlogComment(Integer blogCommentId, Account account,Integer pageIndex) {
        BlogComment blogComment = blogCommentRepository.findById(blogCommentId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"blog comment" + blogCommentId + " Not exist or blog comment was blocked "));
        CheckLikeDislikeReportVo checkLikeOrDislikes = checkLikeDislikeReportBCRepository.getCheckLikeDislikeReportBC(blogCommentId,account.getAccountId());
        CheckLikeDislikeReportBC checkLikeDislikeReportBC = new CheckLikeDislikeReportBC();
        checkLikeDislikeReportBC.setBlogComment(blogComment);
        checkLikeDislikeReportBC.setAccount(account);
        checkLikeDislikeReportBC.setCheckId(new CheckLikeDislikeReportId(account.getAccountId(), blogCommentId));
        if (checkLikeOrDislikes == null) { //ch??a like , dislike hay report bao gi???
            blogComment.setTotalLike(blogComment.getTotalLike()); //gi??? nguy??n total like
            blogComment.setTotalDisLike(blogComment.getTotalDisLike() + 1);// t??ng total dislike l??n 1
            checkLikeDislikeReportBC.setCheckLike(0);//ko like
            checkLikeDislikeReportBC.setCheckDislike(1);//dislike
            checkLikeDislikeReportBC.setCheckReport(0);
        } else if (checkLikeOrDislikes.getCheckDislike() == null || checkLikeOrDislikes.getCheckDislike() != 1) { //ng?????i ????ng nh???p ch??a dislike, ???? like ho???c report
            blogComment.setTotalDisLike(blogComment.getTotalDisLike() + 1); //t??ng total dislike l??n 1
            if (checkLikeOrDislikes.getCheckLike() == 1 && blogComment.getTotalLike() > 0) { //n???u ???? t???ng like
                blogComment.setTotalLike(blogComment.getTotalLike() - 1); //gi???m total like ??i 1
            }
            checkLikeDislikeReportBC.setCheckLike(0); //b??? like
            checkLikeDislikeReportBC.setCheckDislike(1); // dislike
            checkLikeDislikeReportBC.setCheckReport(checkLikeOrDislikes.getCheckReport());// gi??? nguy??n report
        } else { // ng?????i ????ng nh???p ???? dislike => b???m n??t ????? b??? dislike
            if (blogComment.getTotalDisLike() > 0) {
                blogComment.setTotalDisLike(blogComment.getTotalDisLike() - 1); // gi???m total dislike xu???ng 1
            }
            blogComment.setTotalLike(blogComment.getTotalLike());// gi??? nguy??n total like
            checkLikeDislikeReportBC.setCheckDislike(0); //b??? dislike
            checkLikeDislikeReportBC.setCheckLike(checkLikeOrDislikes.getCheckLike()); // gi??? nguy??n like
            checkLikeDislikeReportBC.setCheckReport(checkLikeOrDislikes.getCheckReport());// gi??? nguy??n report
        }
        checkLikeDislikeReportBCRepository.save(checkLikeDislikeReportBC);
        blogCommentRepository.save(blogComment);
        Page<BlogCommentAccountVo> blogCommentAccountVos = findBlogCommentByBlogId(blogRepository.getBlogIdByBlogCommentId(blogCommentId), pageIndex-1,5);
        for (BlogCommentAccountVo blogCommentAccountVo : blogCommentAccountVos) {
            blogCommentAccountVo.setCheckLike(checkLikeDislikeReportBC.getCheckLike());
            blogCommentAccountVo.setCheckDislike(checkLikeDislikeReportBC.getCheckDislike());

            if (blogCommentAccountVo.getAccountUserName().equals(account.getUserName())) {// n???u ng?????i t???o comment l?? ng?????i ????ng nh???p
                blogCommentAccountVo.setCheckEdit(1);// ???????c quy???n edit, delete
            } else {
                blogCommentAccountVo.setCheckEdit(0);// ko dc quy???n edit, delete
            }
        }
        return blogCommentAccountVos;
    }

    @Override
    public ResponseEntity<?> deleteBlogComment(Integer blogCommentId, Account account) {
        BlogComment blogComment = blogCommentRepository.findById(blogCommentId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"blog comment" + blogCommentId + " Not exist or blog comment was blocked "));
        Blog blog = blogComment.getBlogID();
        if (blogComment.getAccount().getAccountId() == account.getAccountId()) {
            if(blog.getNumberComment()>0){
                blog.setNumberComment(blog.getNumberComment()-1);
            }
            blogComment.setStatus(3);
            blogCommentRepository.save(blogComment);
            blogRepository.save(blog);
            return ResponseEntity.ok(new MessageVo("X??a b??nh lu???n th??nh c??ng", "success"));
        } else if (account.getRole().equals("ROLE_ADMIN")) {
            if(blog.getNumberComment()>0){
                blog.setNumberComment(blog.getNumberComment()-1);
            }
            blogComment.setStatus(3);
            blogCommentRepository.save(blogComment);
            blogRepository.save(blog);
            return ResponseEntity.ok(new MessageVo("X??a b??nh lu???n th??nh c??ng", "success"));
        } else {
            return ResponseEntity.ok(new MessageVo("B???n kh??ng c?? quy???n x??a b??nh lu???n n??y", "error"));
        }
    }

    @Override
    public Page<BlogCommentAccountVo> findReportBlogComment(String searchData, Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return blogCommentRepository.findReportBlogComment("%" + searchData.trim() + "%", pageable);
    }

    @Override
    public ResponseEntity<?> approveComment(Integer blogCommentId) {
        BlogComment blogComment = blogCommentRepository.findById(blogCommentId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"blog comment" + blogCommentId + " Not exist or blog comment was blocked "));
            blogComment.setFlag(0);
            blogComment.setStatus(1);
            blogCommentRepository.save(blogComment);
            return ResponseEntity.ok(new MessageVo("???? ph?? duy???t b??nh lu???n", "success"));
    }


}
