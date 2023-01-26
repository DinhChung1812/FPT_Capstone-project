package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.service.BlogCommentService;
import doan.oishii_share_cong_thuc_nau_an.service.DishCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportManageController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Autowired
    private DishCommentService dishCommentService;

    @Autowired
    private BlogCommentService blogCommentService;

    //danh sách những comment bị report
    @GetMapping("/admin/getListDishCommentReport")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getListDishCommentReport(Model model, @RequestParam(required = false) String searchData ,
                                         @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getListDishCommentReport");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishCommentAccountVo> dishCommentAccountVoList = dishCommentService.findReportDishComment(searchData,pageIndex-1, pageSize);
        model.addAttribute("dishCommentAccountVoList", dishCommentAccountVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishCommentAccountVoList.getTotalPages());
        LogUtils.getLog().info("END getListDishCommentReport");
        return ResponseEntity.ok(model);
    }

    @GetMapping("/admin/getListBlogCommentReport")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getListBlogCommentReport(Model model, @RequestParam(required = false) String searchData ,
                                                      @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getListBlogCommentReport");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<BlogCommentAccountVo> blogCommentAccountVos = blogCommentService.findReportBlogComment(searchData,pageIndex-1, pageSize);
        model.addAttribute("dishCommentAccountVoList", blogCommentAccountVos.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", blogCommentAccountVos.getTotalPages());
        LogUtils.getLog().info("END getListBlogCommentReport");
        return ResponseEntity.ok(model);
    }

    @PostMapping ("/admin/approveBlogComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> approveBlogComment(@RequestParam Integer blogCommentId ) {
        return blogCommentService.approveComment(blogCommentId);
    }

    @PostMapping ("/admin/approveDishComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> approveDishComment(@RequestParam Integer dishCommentId ) {
        return dishCommentService.approveComment(dishCommentId);
    }



}
