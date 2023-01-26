package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.BMI.BMIRequest;
import doan.oishii_share_cong_thuc_nau_an.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BMIController {
    @Autowired
    private BMIService bmiService;

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

    @GetMapping("/getInformationBMIUser/{username}")
    public ResponseEntity<?> getInformationBMIUser(@PathVariable("username") String username) {
        return bmiService.getInformationBMIByUser(username);
        //return ResponseEntity.ok(informationBMIUser);
    }

    @PutMapping("/UpdateProfileBMI")
    public ResponseEntity<?> UpdateProfileBMI(@RequestBody BMIRequest bmiRequest) {
        bmiService.updateProfile(bmiRequest);
        return new ResponseEntity<>("update successfull", HttpStatus.OK);
    }

    @GetMapping("/getMainIngredient")
    public ResponseEntity<?> getMainIngredient() {
        List<String> listMainIngredient = dishServive.getListMainIngredient();
        if (listMainIngredient == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageVo("Không có nguyên liệu chính nào!!!", "error"));
        }
        return ResponseEntity.ok(listMainIngredient);
    }

    @GetMapping("/getDishByBMIUser")
    public ResponseEntity<?> getDishByBMIUser(@RequestParam("meal") String meal, @RequestParam("mainIngredient") String mainIngredient, @RequestParam("totalCalo") Double totalCalo) {
        if (totalCalo == null) {
            throw new NotFoundException(StatusCode.Not_Found, "Total calo chưa có yêu cầu bạn tính BMI trước");
        }
        if (mainIngredient == null) {
            throw new NotFoundException(StatusCode.Not_Found, "mainIngredient chưa có!");
        }
        if (meal == null) {
            throw new NotFoundException(StatusCode.Not_Found, "meal chưa có!");
        }
        List<BMIDishDetailVo> dishDetailVo = dishServive.getDishByBMIUser(meal, mainIngredient, totalCalo);

        if (dishDetailVo == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageVo("Món ăn theo nguyên liệu chính " + mainIngredient + " không tồn tại", "error"));
        }
        return ResponseEntity.ok(dishDetailVo);
    }

    @GetMapping("/getListDishByBMIUser")
    public ResponseEntity<?> getListDishByBMIUser(@RequestParam("totalCalo") Double totalCalo) {
        if (totalCalo == null) {
            throw new NotFoundException(StatusCode.Not_Found, "Total calo chưa có yêu cầu bạn tính BMI trước");
        }
        List<BMIDishDetailVo> listDishBySuggest = dishServive.getListDishByBMIUser(totalCalo);
        return ResponseEntity.ok(listDishBySuggest);
    }

    @GetMapping("/searchMainIngredient")
    public ResponseEntity<?> searchMainIngredient(@RequestParam("ingredient") String ingredient) {
        if (ingredient == null) {
            throw new NotFoundException(StatusCode.Not_Found, "Yêu cầu nhập nguyên liệu chính");
        }
        List<String> mainIngredient = dishServive.searchMainIngredient(ingredient);
        return ResponseEntity.ok(mainIngredient);
    }

    @GetMapping("/getDishByCaloBMI")
    public ResponseEntity<?> getDishByCaloBMI(@RequestParam("meal") String meal, @RequestParam("mainIngredient") String mainIngredient, @RequestParam("calo") Double calo, @RequestParam Integer[] listIDDish) {
        if (meal == null) {
            throw new NotFoundException(StatusCode.Not_Found, "meal chưa có");
        }
        if (mainIngredient == null) {
            throw new NotFoundException(StatusCode.Not_Found, "mainIngredient chưa có");
        }
        if (calo == null) {
            throw new NotFoundException(StatusCode.Not_Found, "calo chưa có");
        }

        BMIDishDetailVo listIDDishBySuggest = dishServive.getDishByCaloBMI(meal, mainIngredient, calo, listIDDish);
        return ResponseEntity.ok(listIDDishBySuggest);
    }

    @GetMapping("/getListMainIngredientByMeal")
    public ResponseEntity<?> getListMainIngredientByMeal(@RequestParam("meal") String meal) {
        List<String> listMainIngredient = dishServive.getListMainIngredientByMeal(meal);
        if (listMainIngredient == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageVo("Không có nguyên liệu chính nào!!!", "error"));
        }
        return ResponseEntity.ok(listMainIngredient);
    }

}
