package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.dto.Responds.MessageResponse;
import doan.oishii_share_cong_thuc_nau_an.entities.*;
import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.serviceImpl.DishSeviceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DishSeviceImplTest {

    @Autowired
    DishSeviceImpl dishSevice;

    static Dish objectTest() {
        Dish dish = new Dish();
        dish.setName("gà ");
        dish.setLevel(1);
        dish.setNumberPeopleForDish(3);
        dish.setTime(60);
        dish.setVideo("https://youtu.be/fhEISRprgKc");

        Formula formula = new Formula();
        formula.setDescribe("Gà rửa sạch, lấy 1/2 tô nước cho rượu sake cooking và muối vào khuấy tan. Cho gà vào ngâm 15 phút. Nấm đông cô ngâm nở. Hành lá lấy phần đầu trắng. Chuẩn bị gia vị sốt.");

        Step step1 = new Step();
        step1.setTitle(1);
        step1.setDescribe("Gà rửa sạch, lấy 1/2 tô nước cho rượu sake cooking và muối vào khuấy tan. Cho gà vào ngâm 15 phút. Nấm đông cô ngâm nở. Hành lá lấy phần đầu trắng. Chuẩn bị gia vị sốt.");

        Step step2 = new Step();
        step2.setTitle(2);
        step2.setDescribe("ấy nồi cho nước lượng vừa đủ + nấm, đun sôi. Cho gà và hành lá vào, đậy nắp luộc chín gà. Gà chín vớt ra đĩa, dùng cọ phết đều trong ngoài đùi gà Shoyu (nước tương) đã chuẩn bị, ướp 5 phút");

        List<Step> stepList = new ArrayList<>();
        stepList.add(step1);
        stepList.add(step2);

        formula.setListStep(stepList);

        Account account = new Account();
        account.setUserName("tu123");
        formula.setAccount(account);

        dish.setFormulaId(formula);

        IngredientDetail ingredient1 = new IngredientDetail();
        ingredient1.setName("Đùi gà");
        ingredient1.setQuantity(2);
        ingredient1.setUnit("cái");
        ingredient1.setCalo(300);
        ingredient1.setMainIngredient(1);

        IngredientDetail ingredient2 = new IngredientDetail();
        ingredient2.setName("Rượu Sake");
        ingredient2.setQuantity(1);
        ingredient2.setUnit("muỗng canh");
        ingredient2.setCalo(134);
        ingredient2.setMainIngredient(0);

        IngredientChange change1 = new IngredientChange();
        change1.setName("rượu gạo");
        change1.setQuantity(1);
        change1.setUnit("muỗng canh");

        IngredientChange change2 = new IngredientChange();
        change2.setName("rượu nếp");
        change2.setQuantity(3);
        change2.setUnit("muỗng canh");

        List<IngredientChange> ingredientChangeList = new ArrayList<>();
        ingredientChangeList.add(change1);
        ingredientChangeList.add(change2);

        ingredient2.setIngredientChangeList(ingredientChangeList);

        List<IngredientDetail> ingredientDetailList = new ArrayList<>();

        ingredientDetailList.add(ingredient1);
        ingredientDetailList.add(ingredient2);

        dish.setListIngredientDetail(ingredientDetailList);

        DishImage image1 = new DishImage();
        image1.setUrl("https://image.cooky.vn/recipe/g6/50770/s640/cooky-recipe-cover-r50770.jpg");
        image1.setNote("");

        DishImage image2 = new DishImage();
        image2.setUrl("https://image.cooky.vn/recipe/g6/50770/s640/cooky-recipe-cover-r50770.jpg");
        image2.setNote("");

        List<DishImage> dishImageList = new ArrayList<>();
        dishImageList.add(image1);
        dishImageList.add(image2);

        dish.setListDishImage(dishImageList);

        DishCategory category1 = new DishCategory();
        category1.setDishCategoryID(1);

        DishCategory category2 = new DishCategory();
        category2.setDishCategoryID(2);

        List<DishCategory> dishCategoryList = new ArrayList<>();
        dishCategoryList.add(category1);
        dishCategoryList.add(category2);

        dish.setIdDishCategory(dishCategoryList);

        return dish;
    }

    @Test
    void createNewRecipeNameNull() {
        Dish dish = objectTest();
        dish.setName("");
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu tên món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeLevelNull() {
        Dish dish = objectTest();
        dish.setLevel(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu độ khó món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeVideoNull() {
        Dish dish = objectTest();
        dish.setVideo("");
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu video món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeNumberPeopleForDishNull() {
        Dish dish = objectTest();
        dish.setNumberPeopleForDish(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu số lượng người cho món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeTimeNull() {
        Dish dish = objectTest();
        dish.setTime(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thời gian món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeFormulaNull() {
        Dish dish = objectTest();
        dish.setFormulaId(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu công thức món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeIngredientNull() {
        Dish dish = objectTest();
        dish.setListIngredientDetail(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu nguyên liệu món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeImageNull() {
        Dish dish = objectTest();
        dish.setListDishImage(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu ảnh mô tả món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipeCategoryNull() {
        Dish dish = objectTest();
        dish.setIdDishCategory(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thể loại món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void createNewRecipe() {
        Dish dish = objectTest();
        dish.setName("create "+dish.getName());
        ResponseEntity expected = ResponseEntity.ok(new MessageResponse(StatusCode.Success, "Thêm món ăn thành công"));
        Assert.assertEquals(expected, dishSevice.createNewRecipe(dish));
    }

    @Test
    void editRecipeIdNull() {
        Dish dish = objectTest();
        dish.setDishID(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Not_Found,"Không tìm thấy món ăn"),HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeNameNull() {
        Dish dish = objectTest();
        dish.setName("");
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu tên món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeLevelNull() {
        Dish dish = objectTest();
        dish.setLevel(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu độ khó món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeVideoNull() {
        Dish dish = objectTest();
        dish.setVideo("");
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu video món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeNumberPeopleForDishNull() {
        Dish dish = objectTest();
        dish.setNumberPeopleForDish(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu số lượng người cho món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeTimeNull() {
        Dish dish = objectTest();
        dish.setTime(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thời gian món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeFormulaNull() {
        Dish dish = objectTest();
        dish.setFormulaId(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu công thức món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeIngredientNull() {
        Dish dish = objectTest();
        dish.setListIngredientDetail(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu nguyên liệu món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeImageNull() {
        Dish dish = objectTest();
        dish.setListDishImage(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu ảnh mô tả món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipeCategoryNull() {
        Dish dish = objectTest();
        dish.setIdDishCategory(null);
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thể loại món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void editRecipe() {
        Dish dish = objectTest();
        dish.setName("edit "+dish.getName());
        ResponseEntity expected = ResponseEntity.ok(new MessageResponse(StatusCode.Success, "Cập nhật món ăn thành công"));
        Assert.assertEquals(expected, dishSevice.editRecipe(6012,dish));
    }

    @Test
    void deleteRecipeIDNotExist() {
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Không tìm thấy món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.deleteRecipe(0));
    }

    @Test
    void deleteRecipeIDNull() {
        ResponseEntity expected = new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Không tìm thấy món ăn"), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(expected, dishSevice.deleteRecipe(null));
    }

    @Test
    void deleteRecipet() {
        ResponseEntity expected = ResponseEntity.ok(new MessageResponse(StatusCode.Success, "Xóa món ăn thành công"));
        Assert.assertEquals(expected, dishSevice.deleteRecipe(6014));
    }

}