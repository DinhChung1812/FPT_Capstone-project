package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.*;
import doan.oishii_share_cong_thuc_nau_an.service.IngredientConflictService;
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
public class IngredientConflictController {

    @Value("${pageSize2}")
    private Integer pageSize;
    @Autowired
    private IngredientConflictService ingredientConflictService;

    @GetMapping("/getListIngredientConflict")
//    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> getListIngredientConflict(Model model, @RequestParam(required = false) String searchData,
                                         @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getListIngredientConflict");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<IngredientConflictVo> listIngredientConflict = ingredientConflictService.getListIngredientConflict(searchData,pageIndex-1, pageSize);
        model.addAttribute("listBlogActive", listIngredientConflict.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", listIngredientConflict.getTotalPages());
        LogUtils.getLog().info("END getListIngredientConflict");
        return ResponseEntity.ok(model);
    }


    @GetMapping("/getIngredientConflictInfo")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> getIngredientConflictInfo(@RequestParam Integer ingredientConflictId){
        IngredientConflictVo ingredientConflictVo = ingredientConflictService.getIngredientConflictInfo(ingredientConflictId);
        return ResponseEntity.ok(ingredientConflictVo);
    }

    @PostMapping("/saveIngredientConflict")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> saveIngredientConflict(@Valid @RequestBody SaveIngredientConflictRequest saveIngredientConflictRequest,
                                      Authentication authentication) {
        if(saveIngredientConflictRequest.getIngredientA() == null || saveIngredientConflictRequest.getIngredientA().trim() == ""){
            return ResponseEntity.ok(new MessageVo("xin hãy điền nguyên liệu 1", "error"));
        }
        if(saveIngredientConflictRequest.getIngredientB() == null || saveIngredientConflictRequest.getIngredientB().trim() == ""){
            return ResponseEntity.ok(new MessageVo("xin hãy điền nguyên liệu 2", "error"));
        }
        if(saveIngredientConflictRequest.getConsequence() == null || saveIngredientConflictRequest.getConsequence().trim() == ""){
            return ResponseEntity.ok(new MessageVo("xin hãy điền hệ quả khi ăn cùng lúc 2 nguyên liệu này", "error"));
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (saveIngredientConflictRequest.getIngredientConflictId() == null) {
            return ingredientConflictService.createIngredientConflict(saveIngredientConflictRequest.getIngredientA(),
                     saveIngredientConflictRequest.getIngredientB(), saveIngredientConflictRequest.getConsequence(),userDetails.getUsername());
        } else {
            return ingredientConflictService.updateIngredientConflict(saveIngredientConflictRequest.getIngredientConflictId(),
                    saveIngredientConflictRequest.getIngredientA(),
                    saveIngredientConflictRequest.getIngredientB(), saveIngredientConflictRequest.getConsequence(),userDetails.getUsername());
        }

    }

    @PostMapping("/deleteIngredientConflict")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> deleteIngredientConflict(@RequestParam Integer ingredientConflictId) {
        return ingredientConflictService.deleteIngredientConflict(ingredientConflictId);
    }


}
