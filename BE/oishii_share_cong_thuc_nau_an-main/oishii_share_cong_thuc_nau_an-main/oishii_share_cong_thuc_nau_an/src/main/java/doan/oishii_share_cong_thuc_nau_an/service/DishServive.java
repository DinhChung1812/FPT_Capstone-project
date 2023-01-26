package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishEditResponse;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishResponse;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishSearchResponse;
import doan.oishii_share_cong_thuc_nau_an.entities.Dish;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DishServive {

    List<DishVo> getTop5VoteWeek();

    List<DishVo> getTop5VoteMonth();

    Page<DishFormulaVo> getAllRecipe(String searchData, Integer pageIndex, Integer pageSize);

    Page<DishFormulaVo> getRecipeByCategory(Integer categoryId, Integer pageIndex, Integer pageSize);

    Page<DishFormulaVo> getRecipeOfCreater(String creater, String searchData, Integer pageIndex, Integer pageSize);

    DishDetailVo getDishDetail(Integer dishId);

    DishSearchResponse getDishByName(String name, Integer pageIndex);

    DishSearchResponse getDishByCate(Integer cate,Integer pageIndex);

    List<DishResponse> getTop5New();

    ResponseEntity<?> createNewRecipe(Dish dishRequest);

     ResponseEntity<?> editRecipe(Integer dishId,Dish dishRequest);

    ResponseEntity<?> deleteRecipe(Integer recipeId);

    List<BMIDishDetailVo> getDishByBMIUser(String meal, String mainIngredient, Double calo);

    List<BMIDishDetailVo> getListDishByBMIUser(Double totalCalo);

    List<String> getListMainIngredient();

    DishEditResponse getDishById(Integer id);

    List<String> searchMainIngredient(String ingredient);

    BMIDishDetailVo getDishBMIDetail(Integer dishId);

    BMIDishDetailVo getDishByCaloBMI(String meal, String mainIngredient, Double calo, Integer[] listIdDish);

    List<String> getListMainIngredientByMeal(String meal);
}
