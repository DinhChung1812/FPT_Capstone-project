//package doan.oishii_share_cong_thuc_nau_an.web.controller;
//
//
//import doan.oishii_share_cong_thuc_nau_an.common.vo.*;
//import doan.oishii_share_cong_thuc_nau_an.service.IngredientService;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.DishCategory;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.Ingredient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class IngredientManageController {
//
//    @Autowired
//    private IngredientService ingredientService;
//
//    //danh sách nguyên liệu
//    @GetMapping("/mod/listIngredient")
//    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
//    public ResponseEntity<?> getListIngredient(Model model, @RequestParam(required = false) String searchData,
//                                               @RequestParam(required = false) Integer pageIndex) {
//        if (pageIndex == null) {
//            pageIndex = 1;
//        }
//        Page<IngredientVo> ingredientList = ingredientService.findAllIngredient(searchData,pageIndex-1, 5);
//        model.addAttribute("ingredientList", ingredientList.toList());
//        model.addAttribute("pageIndex", pageIndex);
//        model.addAttribute("numOfPages", ingredientList.getTotalPages());
//        return ResponseEntity.ok(model);
//    }
//
//    //danh sách loại nguyên liệu -> data combobox trên màn create/update nguyên liệu
//    @GetMapping("/mod/listIngredientCategory")
//    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
//    public ResponseEntity<?> getListIngredientCategory() {
//        List<IngredientCategoryVo> ingredientCategory = ingredientService.getListIngredientCategory();
//        return ResponseEntity.ok(ingredientCategory);
//    }
//
//    //danh sách loại đơn vị -> data combobox trên màn create/update nguyên liệu
//    @GetMapping("/mod/listIngredientUnit")
//    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
//    public ResponseEntity<?> getListIngredientUnit() {
//        List<IngredientUnitVo> ingredientUnitVos = ingredientService.getListIngredientUnit();
//        return ResponseEntity.ok(ingredientUnitVos);
//    }
//
//    //nút lưu trên màn create/edit
//    @PostMapping ("/mod/saveIngredient")
//    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
//    public ResponseEntity<?> saveIngredient(@Valid @RequestBody SaveIngredientRequest saveIngredientRequest) {
//        if(ingredientService.save(saveIngredientRequest) != null) {
//            MessageVo messageVo = new MessageVo("lưu nguyên liệu thành công", "info");
//            return ResponseEntity.ok(messageVo);
//        }else {
//            MessageVo messageVo = new MessageVo("lưu nguyên liệu thất bại", "error");
//            return new ResponseEntity<MessageVo>(messageVo, HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    //lấy ra detail nguyên liệu -> data khi vào màn edit
//    @GetMapping ("/mod/getIngredientDetail")
//    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
//    public ResponseEntity<?> getIngredientDetail(@RequestParam(required = true) Integer ingredientID) {
//        IngredientVo ingredient = ingredientService.getIngredientDetail(ingredientID);
//        return ResponseEntity.ok(ingredient);
//    }
//
//    //nút xóa
//    @PostMapping  ("/mod/deleteIngredient")
//    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
//    public ResponseEntity<?> deleteIngredient(@RequestParam(required = true) Integer ingredientID) {
//        if( ingredientService.deleteIngredient(ingredientID)){
//            MessageVo messageVo = new MessageVo("xóa nguyên liệu thành công", "info");
//            return ResponseEntity.ok(messageVo);
//        }else{
//            MessageVo messageVo = new MessageVo("xóa nguyên liệu thất bại", "error");
//            return new ResponseEntity<MessageVo>(messageVo, HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//
//
//
//
//}
