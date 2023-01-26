package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.*;
import doan.oishii_share_cong_thuc_nau_an.repositories.AccountRepository;
import doan.oishii_share_cong_thuc_nau_an.repositories.CheckLikeDislikeReportRepository;
import doan.oishii_share_cong_thuc_nau_an.service.*;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.entities.DishComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RecipeDetailController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Autowired
    private DishServive dishServive;

    @Autowired
    private DishCommentService dishCommentService;

    @Autowired
    private DishImageService dishImageService;

    @Autowired
    private IngredientDetailService ingredientDetailService;

    @Autowired
    private StepService stepService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CheckLikeDislikeReportRepository checkLikeDislikeReportRepository;

    // lấy toàn bộ công thức
    @GetMapping("/getRecipeDetail")
    public ResponseEntity<?> getRecipeDetail(@RequestParam(value = "dishId") Integer dishId) {
        LogUtils.getLog().info("START getRecipeDetail");
        try{
        DishDetailVo dishDetailVo = dishServive.getDishDetail(dishId);
        dishDetailVo.setStepList(stepService.findByFormulaID(dishDetailVo.getFormulaID()));
        dishDetailVo.setDishImageList(dishImageService.findByDishID(dishDetailVo.getDishID()));
        //dishDetailVo.setDishCommentList(dishCommentService.findDishCommentByDishId(dishDetailVo.getDishID()));
        dishDetailVo.setIngredientDetailList(ingredientDetailService.findIngredientDetailVoByDishID(dishDetailVo.getDishID()));
        LogUtils.getLog().info("END getRecipeDetail");
        return ResponseEntity.ok(dishDetailVo);
        }catch (NullPointerException e){
            return ResponseEntity.ok(new MessageVo("công thức này không tồn tại", "error"));
        }catch (Exception e) {
            return ResponseEntity.ok(new MessageVo("có lỗi khi lấy thông tin của công thức này", "error"));
        }
    }

    // lấy danh sách comment của công thức
    @GetMapping("/getListCommentOfRecipe")
    public ResponseEntity<?> getListCommentOfRecipe(Model model, @RequestParam(value = "dishId") Integer dishId,
                                                    @RequestParam(required = false) Integer pageIndex,
                                                    Authentication authentication) {
        LogUtils.getLog().info("START getListCommentOfRecipe");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishCommentAccountVo> dishCommentAccountVoList = dishCommentService.findDishCommentByDishId(dishId,pageIndex-1, pageSize );
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Account account = accountRepository.findAccountByUserName(userDetails.getUsername());
            for (DishCommentAccountVo dishCommentAccountVo : dishCommentAccountVoList) {
                //kiểm tra xem người đăng nhập đã like hay dislike bài đó chưa: 0 chưa, 1 rồi
                CheckLikeDislikeReportVo checkLikeOrDislikes = checkLikeDislikeReportRepository.getCheckLikeDislikeReport(account.getAccountId(), dishCommentAccountVo.getDishCommentID());
                if(checkLikeOrDislikes != null) {
                    dishCommentAccountVo.setCheckLike(checkLikeOrDislikes.getCheckLike());
                    dishCommentAccountVo.setCheckDislike(checkLikeOrDislikes.getCheckDislike());
                }
                if (dishCommentAccountVo.getAccountUserName().equals(account.getUserName())){// nếu người tạo comment là người đăng nhập
                    dishCommentAccountVo.setCheckEdit(1);// được quyền edit, delete
                }else{
                    dishCommentAccountVo.setCheckEdit(0);// ko dc quyền edit, delete
                }
            }
        }
        model.addAttribute("dishCommentAccountVoList", dishCommentAccountVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishCommentAccountVoList.getTotalPages());
        LogUtils.getLog().info("END getListCommentOfRecipe");
        return ResponseEntity.ok(model);

    }

    //lấy nguyên liệu thay thế
//    @GetMapping("/getIngredientChange")
//    public ResponseEntity<?> getIngredientChange(@RequestParam(value = "ingredientDetailId") List<Integer> ingredientDetailId) {
//   //     List<Integer> ingredientDetailIdList = Arrays.asList(ingredientDetailId);
//        List<IngredientChangeVo> ingredientChangeList = ingredientDetailService.getIngredientChange(ingredientDetailId);
//            return ResponseEntity.ok(ingredientChangeList);
//    }

    //nút lưu comment
    @PostMapping("/saveDishComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> saveDishComment(@Valid @RequestBody SaveDishCommentRequest saveDishCommentRequest,
                                             Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername());

        if(saveDishCommentRequest.getDishCommentId() == null){
           return dishCommentService.createComment(saveDishCommentRequest.getDishId(), saveDishCommentRequest.getContent()
                   ,saveDishCommentRequest.getStarRate(),account);
        }else{
           return dishCommentService.updateComment(saveDishCommentRequest.getDishId(),saveDishCommentRequest.getDishCommentId(),
                   saveDishCommentRequest.getContent(),saveDishCommentRequest.getStarRate(),account);
        }
//        MessageVo message = new MessageVo();
//        message.setMessContent("lưu bình luận thành công");
//        message.setMessType("success");
//        return ResponseEntity.ok(message);

    }

    //nút report bình luận
    @PostMapping("/reportDishComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> reportComment( @RequestParam(value = "dishCommentId", required = false) Integer dishCommentId,
                                             Authentication authentication) {
        MessageVo message = new MessageVo();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập

        CheckLikeDislikeReportVo checkReport = checkLikeDislikeReportRepository.getCheckLikeDislikeReport(account.getAccountId(), dishCommentId);
        if(checkReport == null || checkReport.getCheckReport() == null || checkReport.getCheckReport() != 1){ //nếu ng đăng nhập chưa report bình luận
            dishCommentService.reportComment(dishCommentId,account, checkReport);
            message.setMessContent("Đã báo cáo bình luận cho quản trị viên");
            message.setMessType("success");
            return ResponseEntity.ok(message);
        }else {
            message.setMessContent("Bạn đã báo cáo bình luận này rồi");
            message.setMessType("info");
            return ResponseEntity.ok(message);
        }

    }

    //khi bấm nút like bình luận
    @PostMapping("/likeDishComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> likeDishComment( @RequestParam(value = "dishCommentId", required = false) Integer dishCommentId,
                                            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập

        DishComment dishComment = dishCommentService.likeComment(dishCommentId,account);
        MessageVo message = new MessageVo();
        message.setMessContent("Đã bấm nút like bình luận");
        message.setMessType("success");
        return ResponseEntity.ok(message);

    }

    //nút dislike bình luận
    @PostMapping("/dislikeDishComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> dislikeDishComment( @RequestParam(value = "dishCommentId", required = false) Integer dishCommentId,
                                              Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        DishComment dishComment = dishCommentService.dislikeComment(dishCommentId,account);
        MessageVo message = new MessageVo();
        message.setMessContent("Đã bấm nút dislike bình luận");
        message.setMessType("success");
        return ResponseEntity.ok(message);

    }

    // nút xóa bình luận
    @PostMapping("/deleteDishComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteDishComment( @RequestParam(value = "dishCommentId", required = false) Integer dishCommentId,
                                                 Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        DishComment dishComment = dishCommentService.deleteComment(dishCommentId,account);
        MessageVo message = new MessageVo();
        if(dishComment.getStatus() == 3){
            message.setMessContent("Đã xóa bình luận" );
            message.setMessType("success");
        }else {
            message.setMessContent("Không có quyền xóa bình luận");
            message.setMessType("error");
        }
        return ResponseEntity.ok(message);

    }


}
