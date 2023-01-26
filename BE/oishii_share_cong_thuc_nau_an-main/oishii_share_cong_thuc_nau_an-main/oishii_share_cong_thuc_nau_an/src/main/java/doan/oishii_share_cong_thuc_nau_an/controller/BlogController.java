package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.*;
import doan.oishii_share_cong_thuc_nau_an.repositories.*;
import doan.oishii_share_cong_thuc_nau_an.service.BlogCommentService;
import doan.oishii_share_cong_thuc_nau_an.service.BlogService;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
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
public class BlogController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Autowired
    private BlogService blogService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCommentService blogCommentService;

    @Autowired
    private CheckLikeDislikeBlogRepository checkLikeDislikeBlogRepository;

    @Autowired
    private CheckLikeDislikeReportBCRepository checkLikeDislikeReportBCRepository;


    //danh sách blog, màn home của blog, search theo username ng tạo và title
    @GetMapping("/getListBlog")
    public ResponseEntity<?> getListBlog(Model model, @RequestParam(required = false) String searchData,
                                         @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getListBlog");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<BlogVo> listBlogActive = blogService.getListBlogActive(searchData,pageIndex-1, pageSize);
        model.addAttribute("listBlogActive", listBlogActive.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", listBlogActive.getTotalPages());
        LogUtils.getLog().info("END getListBlog");
        return ResponseEntity.ok(model);
    }

    //lấy ra blog chi tiết
    @GetMapping("/getBlogDetail")
    public ResponseEntity<?> getBlogDetail(@RequestParam(value = "blogId") Integer blogId, Authentication authentication) {
        LogUtils.getLog().info("START getBlogDetail");
        BlogVo blogDetail = blogService.getBlogDetail(blogId);
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Account account = accountRepository.findAccountByUserName(userDetails.getUsername());
            //kiểm tra xem người đăng nhập đã like hay dislike bài đó chưa: 0 chưa, 1 rồi
            CheckLikeDislikeReportVo checkLikeOrDislikes = checkLikeDislikeBlogRepository.getCheckLikeDislikeBlog(blogId, account.getAccountId());
            if (checkLikeOrDislikes != null) {
                blogDetail.setCheckLike(checkLikeOrDislikes.getCheckLike());
                blogDetail.setCheckDislike(checkLikeOrDislikes.getCheckDislike());
            }
            if (blogDetail.getUserName().equals(account.getUserName())) {// nếu người tạo comment là người đăng nhập
                blogDetail.setCheckEdit(1);// được quyền edit, delete
            } else {
                blogDetail.setCheckEdit(0);// ko dc quyền edit, delete
            }
        }
        LogUtils.getLog().info("END getBlogDetail");
        return ResponseEntity.ok(blogDetail);
    }

    //create/update blog
    @PostMapping("/saveBlog")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> saveBlog(@Valid @RequestBody SaveBlogRequest saveBlogRequest,
                                      Authentication authentication) {
        if(saveBlogRequest.getTitle() == null || saveBlogRequest.getTitle().trim() == ""){
            return ResponseEntity.ok(new MessageVo("xin hãy điền tiêu đề cho bài viết", "error"));
        }
        if(saveBlogRequest.getContent() == null || saveBlogRequest.getContent().trim() == ""){
            return ResponseEntity.ok(new MessageVo("xin hãy điền nội dung cho bài viết", "error"));
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername());
        if (saveBlogRequest.getBlogId() == null) {
            return blogService.createBlog(saveBlogRequest.getTitle(), saveBlogRequest.getContent(), account);
        } else {
            return blogService.updateBlog(saveBlogRequest.getBlogId(), saveBlogRequest.getTitle(), saveBlogRequest.getContent(), account);
        }

    }

    //xóa blog
    @PostMapping("/deleteBlog")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteBlog(@RequestParam(value = "blogId") Integer blogId,
                                        Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        return blogService.deleteBlog(blogId, account);
    }

    //danh sách blog chờ phê duyệt
    @GetMapping("/admin/listBlogPending")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getListBlogPending(Model model,@RequestParam(required = false) String searchData,
                                                @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getListBlogPending");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<BlogVo> listBlogPending = blogService.getListBlogPending(searchData, pageIndex-1, pageSize);
        model.addAttribute("listBlogPending", listBlogPending.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", listBlogPending.getTotalPages());
        LogUtils.getLog().info("END getListBlogPending");
        return ResponseEntity.ok(model);
    }

    //phê duyệt blog
    @PostMapping("/admin/approveBlog")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> approveBlog(@RequestParam(value = "blogId") Integer blogId) {
        return blogService.approveBlog(blogId);
    }

    // like blog
    @PostMapping("/likeBlog")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> likeBlog(@RequestParam(value = "blogId") Integer blogId,
                                             Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        return blogService.likeBlog(blogId, account);

    }

    @PostMapping("/dislikeBlog")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> dislikeBlog(@RequestParam(value = "blogId") Integer blogId,
                                             Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        return blogService.dislikeBlog(blogId, account);

    }
    //-----------------------------------------

    @GetMapping("/getBlogComment")
    public ResponseEntity<?> getBlogComments(Model model,@RequestParam(value = "blogId") Integer blogId,
                                             @RequestParam(required = false) Integer pageIndex,
                                             Authentication authentication) {
        LogUtils.getLog().info("START getBlogComments");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<BlogCommentAccountVo> blogCommentAccountVos = blogCommentService.findBlogCommentByBlogId(blogId, pageIndex-1,pageSize);
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Account account = accountRepository.findAccountByUserName(userDetails.getUsername());
            for (BlogCommentAccountVo blogCommentAccountVo : blogCommentAccountVos) {
                //kiểm tra xem người đăng nhập đã like hay dislike bài đó chưa: 0 chưa, 1 rồi
                CheckLikeDislikeReportVo checkLikeOrDislikes = checkLikeDislikeReportBCRepository.getCheckLikeDislikeReportBC(blogCommentAccountVo.getBlogCommentID(),account.getAccountId());
                if(checkLikeOrDislikes != null) {
                    blogCommentAccountVo.setCheckLike(checkLikeOrDislikes.getCheckLike());
                    blogCommentAccountVo.setCheckDislike(checkLikeOrDislikes.getCheckDislike());
                }
                if (blogCommentAccountVo.getAccountUserName().equals(account.getUserName())){// nếu người tạo comment là người đăng nhập
                    blogCommentAccountVo.setCheckEdit(1);// được quyền edit, delete
                }else{
                    blogCommentAccountVo.setCheckEdit(0);// ko dc quyền edit, delete
                }
            }
        }
        model.addAttribute("blogCommentAccountVos", blogCommentAccountVos.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", blogCommentAccountVos.getTotalPages());
        LogUtils.getLog().info("END getBlogComments");
        return ResponseEntity.ok(model);

    }

    @PostMapping("/saveBlogComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> saveBlogComment(@Valid @RequestBody SaveBlogCommentRequest saveBlogCommentRequest,
                                             Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername());

        if(saveBlogCommentRequest.getBlogCommentId() == null){
            return blogCommentService.createComment(saveBlogCommentRequest.getBlogId(), saveBlogCommentRequest.getContent(),account);
        }else{
            return blogCommentService.updateComment(saveBlogCommentRequest.getBlogId(),saveBlogCommentRequest.getBlogCommentId(),
                    saveBlogCommentRequest.getContent(),account);
        }
    }

    @PostMapping("/reportBlogComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> reportComment( @RequestParam(value = "blogCommentId", required = false) Integer blogCommentId,
                                            Authentication authentication) {
        MessageVo message = new MessageVo();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        return blogCommentService.reportComment(blogCommentId, account);

    }

    @PostMapping("/likeBlogComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> likeBlogComment(Model model,@RequestParam(value = "blogCommentId") Integer blogCommentId,
                                             @RequestParam(required = false) Integer pageIndex,
                                      Authentication authentication) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        Page<BlogCommentAccountVo> blogCommentAccountVos = blogCommentService.likeBlogComment(blogCommentId, account, pageIndex);
        model.addAttribute("blogCommentAccountVos", blogCommentAccountVos.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", blogCommentAccountVos.getTotalPages());
        return ResponseEntity.ok(model);

    }

    @PostMapping("/dislikeBlogComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> dislikeBlogComment(Model model,@RequestParam(value = "blogCommentId") Integer blogCommentId,
                                                @RequestParam(required = false) Integer pageIndex,
                                         Authentication authentication) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        Page<BlogCommentAccountVo> blogCommentAccountVos = blogCommentService.dislikeBlogComment(blogCommentId, account, pageIndex);
        model.addAttribute("blogCommentAccountVos", blogCommentAccountVos.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", blogCommentAccountVos.getTotalPages());
        return ResponseEntity.ok(model);

    }

    @PostMapping("/deleteBlogComment")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteBlogComment(@RequestParam(value = "blogCommentId") Integer blogCommentId,
                                        Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        return blogCommentService.deleteBlogComment(blogCommentId, account);
    }

}
