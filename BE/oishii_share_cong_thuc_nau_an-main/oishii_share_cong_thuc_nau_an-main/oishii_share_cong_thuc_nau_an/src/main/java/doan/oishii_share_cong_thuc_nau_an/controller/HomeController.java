package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileEditRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishResponse;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishSearchResponse;
import doan.oishii_share_cong_thuc_nau_an.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

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
    private CategoryService categoryService;

    @Autowired
    private HomeService homeService;

    @GetMapping("/getTop5VoteWeek")
    public ResponseEntity<?> getTop5VoteWeek() {
        LogUtils.getLog().info("START getTop5VoteWeek");
        List<DishVo> top5VoteWeek = dishServive.getTop5VoteWeek();
        for(DishVo dishVo : top5VoteWeek){
            List<DishImageVo> imageList = dishImageService.findByDishID(dishVo.getDishId());
            if(null != imageList && imageList.size() != 0) {
                dishVo.setUrlImage(imageList.get(0).getUrl());
            }
            if(dishVo.getAvgStarRate() == null){
                dishVo.setAvgStarRate(0.0);
            }
        }
        LogUtils.getLog().info("END getTop5VoteWeek");
        return ResponseEntity.ok(top5VoteWeek);

    }

    @GetMapping("/getTop5VoteMonth")
    public ResponseEntity<?> getTop5VoteMonth() {
        LogUtils.getLog().info("START getTop5VoteMonth");
        List<DishVo> top5VoteMonth = dishServive.getTop5VoteMonth();
        for(DishVo dishVo : top5VoteMonth){
            List<DishImageVo> imageList = dishImageService.findByDishID(dishVo.getDishId());
            if(null != imageList && imageList.size() != 0) {
                dishVo.setUrlImage(imageList.get(0).getUrl());
            }
            if(dishVo.getAvgStarRate() == null){
                dishVo.setAvgStarRate(0.0);
            }
        }
        LogUtils.getLog().info("END getTop5VoteMonth");
        return ResponseEntity.ok(top5VoteMonth);

    }

    @GetMapping("/getCategories")
    public ResponseEntity<?> getAllCategories() {
        LogUtils.getLog().info("START getAllCategories");
        List<DishCategoryVo> listCategory = categoryService.findAllCategory();
        LogUtils.getLog().info("END getAllCategories");
        return ResponseEntity.ok(listCategory);
    }



    //get profile by id
    // http://localhost:8080/getprofile?profile_id=1
    @GetMapping("/getprofile")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<ProfileRequest> getUserProfile(@RequestParam("profile_id") Integer profileId) {
        ProfileRequest profileRequest = homeService.getProfile(profileId);
        return ResponseEntity.ok(profileRequest);
    }

    //update profile by id
    // http://localhost:8080/updateprofile?profile_id=1
    @PutMapping("/updateprofile")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> UpdateProfile(@RequestParam("profile_id") Integer profileId, @RequestBody ProfileEditRequest profileRequest)  {
        return homeService.updateProfile(profileId, profileRequest);
    }

    @PutMapping("/updateimage")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')or hasRole('ROLE_USER')")
    public ResponseEntity<?> updateImage(@RequestParam("profile_id") Integer profileId,@RequestBody String image){
        return homeService.updateImage(profileId,image);
    }


    //search recipe by name
    //http://localhost:8080/searchdishbyname?name=c
    @GetMapping("/searchdishbyname")
    public ResponseEntity<?> getDishByName(@RequestParam("name") String name,@RequestParam(value = "page_index",required = false)Integer pageIndex) {
        DishSearchResponse dishes = dishServive.getDishByName(name,pageIndex);
        return ResponseEntity.ok(dishes);
    }

//    search recipe by cate
//    http://localhost:8080/searchdishbycate?cateId=1
    @GetMapping("/searchdishbycate")
    public ResponseEntity<?> getDishBycate(@RequestParam("cateId") Integer cate,@RequestParam(value = "page_index",required = false)Integer pageIndex) {
        DishSearchResponse dishes = dishServive.getDishByCate(cate,pageIndex);
        return ResponseEntity.ok(dishes);
    }

    //get 5 recipe newest
    //http://localhost:8080/gettop5new
    @GetMapping("/gettop5new")
    public ResponseEntity<List<DishResponse>> getTop5New(){
        List<DishResponse> dishResponseList = dishServive.getTop5New();
        return ResponseEntity.ok(dishResponseList);
    }


}
