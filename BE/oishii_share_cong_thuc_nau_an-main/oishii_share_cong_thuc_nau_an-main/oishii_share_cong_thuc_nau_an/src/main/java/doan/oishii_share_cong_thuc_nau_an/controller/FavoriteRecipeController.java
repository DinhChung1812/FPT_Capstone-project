package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.AccountRepository;
import doan.oishii_share_cong_thuc_nau_an.service.FavoriteRecipeService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FavoriteRecipeController {

    @Autowired
    private AccountRepository accountRepository;
    @Value("${pageSize}")
    private Integer pageSize;
    @Autowired
    private FavoriteRecipeService favoriteRecipeService;

    @GetMapping ("/getFavoriteRecipes")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> getFavoriteRecipes(Model model,
                                                @RequestParam(required = false) String searchData,
                                                @RequestParam(required = false) Integer pageIndex,
                                                Authentication authentication) {
        LogUtils.getLog().info("START getFavoriteRecipes");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
            Page<DishVo> listFavorite = favoriteRecipeService.getFavoriteRecipe(account.getAccountId(), searchData, pageIndex - 1, pageSize);
            model.addAttribute("listAccountActive", listFavorite.toList());
            model.addAttribute("pageIndex", pageIndex);
            model.addAttribute("numOfPages", listFavorite.getTotalPages());
            LogUtils.getLog().info("END getFavoriteRecipes");
            return ResponseEntity.ok(model);
    }

    @PostMapping  ("/addFavoriteRecipes")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> addFavoriteRecipes(@RequestParam(value = "dishId") Integer dishId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        return favoriteRecipeService.addFavoriteRecipes(dishId, account);
    }

    @PostMapping  ("/removeFavoriteRecipes")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> removeFavoriteRecipes(@RequestParam(value = "dishId") Integer dishId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findAccountByUserName(userDetails.getUsername()); //lấy ra thông tin ng đăng nhập
        return favoriteRecipeService.removeFavoriteRecipes(dishId, account);
    }


}
