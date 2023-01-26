package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.entities.CheckLikeDislikeReport;
import doan.oishii_share_cong_thuc_nau_an.entities.CheckLikeDislikeReportId;
import doan.oishii_share_cong_thuc_nau_an.entities.DishComment;
import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.repositories.CheckLikeDislikeReportRepository;
import doan.oishii_share_cong_thuc_nau_an.repositories.DishCommentRepository;
import doan.oishii_share_cong_thuc_nau_an.repositories.DishRepository;
import doan.oishii_share_cong_thuc_nau_an.service.DishCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DishCommentServiceImpl implements DishCommentService {

    @Autowired
    private DishCommentRepository dishCommentRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CheckLikeDislikeReportRepository checkLikeDislikeReportRepository;

    @Override
    public Page<DishCommentAccountVo> findDishCommentByDishId(Integer dishId, Integer pageIndex, Integer pageSize ) {;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return dishCommentRepository.findDishCommentByDishId(dishId, pageable);
    }

    @Override
    public Page<DishCommentAccountVo> findReportDishComment(String searchData,Integer pageIndex, Integer pageSize ) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return dishCommentRepository.findReportDishComment("%" + searchData.trim() + "%", pageable);
    }


    @Override
    public ResponseEntity<?> createComment(Integer dishId, String content, Integer starRate, Account account) {

        DishComment dishComment = new DishComment();
        dishComment.setAccount(account);
        dishComment.setFlag(0);
        dishComment.setContent(content);
        dishComment.setDishID(dishRepository.findById(dishId).orElseThrow(() -> new NotFoundException(StatusCode.Not_Found,"dish " + dishId + " Not exist or dish was blocked ")));
        dishComment.setStartRate(starRate);
        dishComment.setCreateDate(LocalDate.now());
        dishComment.setUpdateDate(LocalDate.now());
        dishComment.setTotalDisLike(0);
        dishComment.setTotalLike(0);
        dishComment.setStatus(1); // status = 1 active
        dishCommentRepository.save(dishComment);
        return ResponseEntity.ok( new MessageVo("lưu bình luận thành công", "success"));
    }

    @Override
    public ResponseEntity<?> updateComment(Integer dishId, Integer dishCommentId, String content, Integer starRate, Account account) {
        DishComment dishComment = dishCommentRepository.findById(dishCommentId).orElseThrow(() -> new NotFoundException(StatusCode.Not_Found,"dish comment" + dishCommentId + " Not exist or dish comment was blocked "));
        //DishComment updateDishComment = new DishComment();
        if(dishComment.getAccount().getAccountId() == account.getAccountId()){
        dishComment.setAccount(account);
        //updateDishComment.setFlag(dishComment.getFlag());
        dishComment.setContent(content);
        //updateDishComment.setDishID(dishComment.getDishID());
        dishComment.setStartRate(starRate);
        dishComment.setUpdateDate(LocalDate.now());
        //updateDishComment.setCreateDate(dishComment.getCreateDate());
        //updateDishComment.setTotalDisLike(dishComment.getTotalDisLike());
       // updateDishComment.setTotalLike(dishComment.getTotalLike());
        dishComment.setStatus(1);
        //updateDishComment.setDishCommentID(dishCommentId);
        dishCommentRepository.save(dishComment);
        return ResponseEntity.ok( new MessageVo("cập nhật bình luận thành công", "success"));
        } else{
            return ResponseEntity.ok( new MessageVo("Bạn không có quyền sửa bình luận này", "error"));
        }
    }

    @Override
    @Transactional
    public DishComment reportComment(Integer dishCommentId, Account account, CheckLikeDislikeReportVo checkReport) {
        DishComment dishComment = dishCommentRepository.findById(dishCommentId).orElseThrow(() -> new NotFoundException(StatusCode.Not_Found,"dish comment" + dishCommentId + " Not exist or dish comment was blocked "));
        dishComment.setFlag(dishComment.getFlag()+1);
        if(dishComment.getFlag()>=3){ // nếu có 3 ng đổ lên report
            dishComment.setStatus(2); //chuyển trạng thái sang danh sách báo cáo cho admin
        }
        CheckLikeDislikeReport checkLikeDislikeReport = new CheckLikeDislikeReport();
        if(checkReport == null){
            checkLikeDislikeReport.setCheckReport(1);
            checkLikeDislikeReport.setCheckDislike(0);
            checkLikeDislikeReport.setCheckLike(0);
            checkLikeDislikeReport.setDishComment(dishComment);
            checkLikeDislikeReport.setAccount(account);
            checkLikeDislikeReportRepository.save(checkLikeDislikeReport);
        }else {
            checkLikeDislikeReportRepository.updateCheckReport(account.getAccountId(), dishCommentId);
        }
        return dishCommentRepository.save(dishComment);
    }

    @Override
    @Transactional
    public DishComment likeComment(Integer dishCommentId, Account account) {
        DishComment dishComment = dishCommentRepository.findById(dishCommentId).orElseThrow(() -> new NotFoundException(StatusCode.Not_Found,"dish comment" + dishCommentId + " Not exist or dish comment was blocked "));
//        dishComment.setTotalLike(dishComment.getTotalLike()+1);
//        if(dishComment.getTotalDisLike()>0) {
//            dishComment.setTotalDisLike(dishComment.getTotalDisLike() - 1);
//        }
        CheckLikeDislikeReportVo checkLikeOrDislikes = checkLikeDislikeReportRepository.getCheckLikeDislikeReport(account.getAccountId(), dishCommentId);
        CheckLikeDislikeReport checkLikeDislikeReport = new CheckLikeDislikeReport();
        checkLikeDislikeReport.setDishComment(dishComment);
        checkLikeDislikeReport.setAccount(account);
        checkLikeDislikeReport.setCheckId(new CheckLikeDislikeReportId(account.getAccountId(), dishCommentId));
        if(checkLikeOrDislikes == null){ //chưa like , dislike hay report bao giờ
            dishComment.setTotalLike(dishComment.getTotalLike()+1); //tăng total like lên 1
            dishComment.setTotalDisLike(dishComment.getTotalDisLike());// giữ nguyên total dislike
            checkLikeDislikeReport.setCheckLike(1);//like
            checkLikeDislikeReport.setCheckDislike(0);//ko dislike
            checkLikeDislikeReport.setCheckReport(0);

        }else if( checkLikeOrDislikes.getCheckLike() ==null||checkLikeOrDislikes.getCheckLike()!=1){ //người đăng nhập chưa like, đã dislike hoặc report
            dishComment.setTotalLike(dishComment.getTotalLike()+1); //tăng total like lên 1
            if(checkLikeOrDislikes.getCheckDislike() == 1 && dishComment.getTotalDisLike()>0){ //nếu đã từng dislike
                dishComment.setTotalDisLike(dishComment.getTotalDisLike() - 1); //giảm total dislike đi 1
            }
            checkLikeDislikeReport.setCheckLike(1); //like
            checkLikeDislikeReport.setCheckDislike(0); //bỏ dislike
            checkLikeDislikeReport.setCheckReport(checkLikeOrDislikes.getCheckReport());// giữ nguyên report

        }else{ // người đăng nhập đã like => bấm nút để bỏ like
            if(dishComment.getTotalLike()>0) {
                dishComment.setTotalLike(dishComment.getTotalLike() - 1); // giảm total like xuống 1
            }
            dishComment.setTotalDisLike(dishComment.getTotalDisLike());// giữ nguyên total dislike
            checkLikeDislikeReport.setCheckLike(0); //bỏ like
            checkLikeDislikeReport.setCheckDislike(checkLikeOrDislikes.getCheckDislike()); // giữ nguyên dislike
            checkLikeDislikeReport.setCheckReport(checkLikeOrDislikes.getCheckReport());// giữ nguyên report

        }
        checkLikeDislikeReportRepository.save(checkLikeDislikeReport);
        return dishCommentRepository.save(dishComment);
    }

    @Override
    public DishComment dislikeComment(Integer dishCommentId, Account account) {
        DishComment dishComment = dishCommentRepository.findById(dishCommentId).orElseThrow(() -> new NotFoundException(StatusCode.Not_Found,"dish comment" + dishCommentId + " Not exist or dish comment was blocked "));
        CheckLikeDislikeReportVo checkLikeOrDislikes = checkLikeDislikeReportRepository.getCheckLikeDislikeReport(account.getAccountId(), dishCommentId);
        CheckLikeDislikeReport checkLikeDislikeReport = new CheckLikeDislikeReport();
        checkLikeDislikeReport.setDishComment(dishComment);
        checkLikeDislikeReport.setAccount(account);
        checkLikeDislikeReport.setCheckId(new CheckLikeDislikeReportId(account.getAccountId(), dishCommentId));
        if(checkLikeOrDislikes == null){ //chưa like , dislike hay report bao giờ
            dishComment.setTotalLike(dishComment.getTotalLike()); //giữ nguyên total like
            dishComment.setTotalDisLike(dishComment.getTotalDisLike()+1);// tăng total dislike lên 1
            checkLikeDislikeReport.setCheckLike(0);//ko like
            checkLikeDislikeReport.setCheckDislike(1);//dislike
            checkLikeDislikeReport.setCheckReport(0);

        }else if( checkLikeOrDislikes.getCheckDislike() ==null||checkLikeOrDislikes.getCheckDislike()!=1){ //người đăng nhập chưa dislike, đã like hoặc report
            dishComment.setTotalDisLike(dishComment.getTotalDisLike()+1); //tăng total dislike lên 1
            if(checkLikeOrDislikes.getCheckLike() == 1 && dishComment.getTotalLike()>0){ //nếu đã từng like
                dishComment.setTotalLike(dishComment.getTotalLike() - 1); //giảm total like đi 1
            }
            checkLikeDislikeReport.setCheckLike(0); //bỏ like
            checkLikeDislikeReport.setCheckDislike(1); // dislike
            checkLikeDislikeReport.setCheckReport(checkLikeOrDislikes.getCheckReport());// giữ nguyên report

        }else{ // người đăng nhập đã dislike => bấm nút để bỏ dislike
            if(dishComment.getTotalDisLike()>0) {
                dishComment.setTotalDisLike(dishComment.getTotalDisLike() - 1); // giảm total dislike xuống 1
            }
            dishComment.setTotalLike(dishComment.getTotalLike());// giữ nguyên total like
            checkLikeDislikeReport.setCheckDislike(0); //bỏ dislike
            checkLikeDislikeReport.setCheckLike(checkLikeOrDislikes.getCheckLike()); // giữ nguyên like
            checkLikeDislikeReport.setCheckReport(checkLikeOrDislikes.getCheckReport());// giữ nguyên report

        }
        checkLikeDislikeReportRepository.save(checkLikeDislikeReport);
        return dishCommentRepository.save(dishComment);
    }

    @Override
    public DishComment deleteComment(Integer dishCommentId, Account account) {
        DishComment dishComment = dishCommentRepository.findById(dishCommentId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"dish comment" + dishCommentId + " Not exist or dish comment was blocked "));
        if(account.getRole().equals("ROLE_ADMIN") || account.getRole().equals("ROLE_MOD")){
            dishComment.setStatus(3);
        }else if(account.getRole().equals("ROLE_USER") && dishComment.getAccount().getAccountId().equals(account.getAccountId()) ){
            dishComment.setStatus(3);
        }
        return dishCommentRepository.save(dishComment);
    }

    @Override
    public ResponseEntity<?> approveComment(Integer dishCommentId) {
        DishComment dishComment = dishCommentRepository.findById(dishCommentId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"dish comment" + dishCommentId + " Not exist or dish comment was blocked "));
        dishComment.setFlag(0);
        dishComment.setStatus(1);
        dishCommentRepository.save(dishComment);
        return ResponseEntity.ok(new MessageVo("Đã phê duyệt bình luận", "success"));
    }
}
