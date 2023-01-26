package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.*;
import doan.oishii_share_cong_thuc_nau_an.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RecipeCategoryManageController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/getListCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getListCategory(Model model, @RequestParam(required = false) String searchData,
                                         @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getListCategory");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishCategoryVo> listCategory = categoryService.getListCategory(searchData,pageIndex-1, pageSize);
        model.addAttribute("listCategory", listCategory.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", listCategory.getTotalPages());
        LogUtils.getLog().info("END getListCategory");
        return ResponseEntity.ok(model);
    }

    @GetMapping("/admin/getCategoryInfo")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getCategoryInfo(@RequestParam Integer categoryId) {
        return categoryService.getCategoryInfo(categoryId);
    }

    @PostMapping("/admin/saveCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody SaveCategoryRequest saveCategoryRequest) {
        if(saveCategoryRequest.getCategoryName() == null || saveCategoryRequest.getCategoryName().trim() == ""){
            return ResponseEntity.ok(new MessageVo("xin hãy điền tên loại", "error"));
        }
        if(saveCategoryRequest.getCategoryImage() == null || saveCategoryRequest.getCategoryImage().trim() == ""){
            return ResponseEntity.ok(new MessageVo("xin hãy thêm ảnh loại", "error"));
        }
        if (saveCategoryRequest.getCategoryId() == null) {
            return categoryService.createCategory(saveCategoryRequest.getCategoryName(), saveCategoryRequest.getCategoryImage());
        } else {
            return categoryService.updateCategory(saveCategoryRequest.getCategoryId()
                    ,saveCategoryRequest.getCategoryName(), saveCategoryRequest.getCategoryImage());
        }

    }

    @PostMapping("/admin/deleteCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@RequestParam Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }



}
