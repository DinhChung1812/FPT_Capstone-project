package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishEditResponse;
import doan.oishii_share_cong_thuc_nau_an.service.DishServive;
import doan.oishii_share_cong_thuc_nau_an.service.IngredientDetailService;
import doan.oishii_share_cong_thuc_nau_an.entities.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeManageController {

    @Value("${pageSize}")
    private Integer pageSize;
    @Autowired
    private DishServive dishServive;

    @Autowired
    private IngredientDetailService ingredientDetailService;

    //lấy danh sách quản lý tất cả công thức
    @GetMapping("/admin/listRecipe")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllRecipe(Model model,
                                          @RequestParam(required = false) String searchData, @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getAllRecipe");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishFormulaVo> dishFormulaVoList = dishServive.getAllRecipe(searchData, pageIndex-1,pageSize );
        model.addAttribute("dishFormulaVoList", dishFormulaVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishFormulaVoList.getTotalPages());
        LogUtils.getLog().info("END getAllRecipe");
        return ResponseEntity.ok(model);
    }

    @GetMapping("/admin/listRecipeByCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getRecipeByCategory(Model model,
                                          @RequestParam Integer categoryId, @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START listRecipeByCategory");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishFormulaVo> dishFormulaVoList = dishServive.getRecipeByCategory(categoryId, pageIndex-1, pageSize);
        if(dishFormulaVoList.toList() == null || dishFormulaVoList.toList().size() == 0){
            return ResponseEntity.ok(new MessageVo("Không tìm thấy công thức của thể loại", "info"));
        }
        model.addAttribute("dishFormulaVoList", dishFormulaVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishFormulaVoList.getTotalPages());
        LogUtils.getLog().info("END listRecipeByCategory");
        return ResponseEntity.ok(model);
    }

    //lấy danh sách quản lý  công thức của 1 người tạo
    @GetMapping("/mod/listRecipeOfCreater")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> getRecipeOfCreater(Model model,
                                                @RequestParam(value = "creater") String creater,
                                                @RequestParam(required = false) String searchData,@RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getRecipeOfCreater");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishFormulaVo> dishFormulaVoList = dishServive.getRecipeOfCreater(creater, searchData,pageIndex-1, pageSize);
        model.addAttribute("dishFormulaVoList", dishFormulaVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishFormulaVoList.getTotalPages());
        LogUtils.getLog().info("END getRecipeOfCreater");
        return ResponseEntity.ok(model);
    }

    @PostMapping("/mod/createrecipe")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> createRecipe(@RequestBody Dish dishRequest) {
        return dishServive.createNewRecipe(dishRequest);
    }

    @GetMapping("/mod/getdishbyid")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?>getDishById(@RequestParam("dish_id")Integer id){
        DishEditResponse dishEditResponse = dishServive.getDishById(id);
        return ResponseEntity.ok(dishEditResponse);
    }

    @PutMapping("/mod/editrecipe")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> updateRecipe(@RequestParam("recipe_id") Integer dishId ,@RequestBody Dish dishRequest) {
        return dishServive.editRecipe(dishId,dishRequest);

    }

    @DeleteMapping("/mod/deleterecipe")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> deleteRecipe(@RequestParam("recipe_id") Integer recipeId) {
        return dishServive.deleteRecipe(recipeId);

    }

}
