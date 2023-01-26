package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.MessageResponse;
import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.service.BMIService;
import doan.oishii_share_cong_thuc_nau_an.service.DishServive;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class BMIControllerTest {

    @Autowired
    private DishServive dishServive;

    @Autowired
    private BMIService BMIService;

    @Test
    void  testGetListMainIngredientByMeal() throws Exception{
        List<String> listMainIngredientByMeal = new ArrayList<>();
        listMainIngredientByMeal.add("Mướp đắng(Khổ qua)");
        listMainIngredientByMeal.add("Thịt gà");
        listMainIngredientByMeal.add("Bí dao");
        listMainIngredientByMeal.add("Thịt lợn xay");
        listMainIngredientByMeal.add("Khoai mỡ");
        listMainIngredientByMeal.add("Tôm");
        listMainIngredientByMeal.add("Thịt bò");
        listMainIngredientByMeal.add("Hành tây");
        listMainIngredientByMeal.add("Bí đỏ");
        listMainIngredientByMeal.add("Cải thảo");
        listMainIngredientByMeal.add("Thịt lợn");

        Assert.assertEquals(listMainIngredientByMeal, dishServive.getListMainIngredientByMeal("Bữa Trưa"));

    }

    @Test
    void  testSearchMainIngredient() throws Exception{
        List<String> listMainIngredient = new ArrayList<>();
        listMainIngredient.add("Trứng gà");
        listMainIngredient.add("Sụn gà");
        listMainIngredient.add("Chân gà");
        listMainIngredient.add("Lòng gà");
        listMainIngredient.add("Cánh gà");
        listMainIngredient.add("Thịt gà");
        listMainIngredient.add("Nấm đùi gà");

        Assert.assertEquals(listMainIngredient, dishServive.searchMainIngredient("ga"));
    }

    @Test
    void  testGetDishByBMIUser() throws Exception{
        List<BMIDishDetailVo> listDishByBMIUser = new ArrayList<>();
        List<DishImageVo> listImage =new ArrayList<>();
        listImage.add(new DishImageVo(23,"https://firebasestorage.googleapis.com/v0/b/oishi-cook.appspot.com/o/images%2Fc4.jpg?alt=media&token=c8b23fc1-0e71-4123-ac48-fbd80eb57c29",""));
        listImage.add(new DishImageVo(24,"https://firebasestorage.googleapis.com/v0/b/oishi-cook.appspot.com/o/images%2Fc3.jpg?alt=media&token=2e872a22-c5f9-4200-85f8-b929620da00f",""));
        listImage.add(new DishImageVo(25,"https://firebasestorage.googleapis.com/v0/b/oishi-cook.appspot.com/o/images%2Fc1.jpg?alt=media&token=07374df4-320e-4a3f-86b3-86751cf7d168",""));
        listImage.add(new DishImageVo(26,"https://firebasestorage.googleapis.com/v0/b/oishi-cook.appspot.com/o/images%2Fc2.jpg?alt=media&token=2b2747eb-95e5-42f0-bbea-270e90a078a3",""));
        listDishByBMIUser.add(new BMIDishDetailVo(5, "Canh bí đao","vn", 453,2,1,null, "2022-12-15", "https://firebasestorage.googleapis.com/v0/b/oishi-cook.appspot.com/o/videos%2FCANH%20B%C3%8D%20%C4%90AO%20TH%E1%BB%8AT%20B%E1%BA%B0M.mp4?alt=media&token=61871d14-b3f1-47fd-9a5d-30f6efb649ba", "Bữa Trưa",null,null,14,3,4.7,null,null,1225.0,null,772.0, 5,"Bí xanh có vị ngọt, tính mát, có tác dụng thanh nhiệt cơ thể, làm mát ruột, lợi tiểu, giải độc và giảm béo hiệu quả. ", "namnd","",23, null, listImage, null,null ));
        Assert.assertEquals(listDishByBMIUser, dishServive.getDishByBMIUser("Bữa trưa", "Bí dao", 3500.0));
    }

    @Test
    void  testGetInformationBMIUser() throws Exception{
        BMIVo informationBMIUser = new BMIVo("namnd", "Nguyễn Danh Nam", LocalDate.of(1999, 12, 16),"Nam", 170.0,55.0,1.5,2770.0,"Tăng cân", 16.18,"Cân nặng thấp (gầy)","Chỉ số BMI là chỉ số đo cân nặng của một người. Công thức BMI được áp dụng cho cả nam và nữ và chỉ áp dụng cho người trưởng thành (trên 18 tuổi), không áp dụng cho phụ nữ mang thai, vận động viên, người già và có sự thay đổi giữa các quốc gia.");
        System.out.println(informationBMIUser);
        ResponseEntity expected = ResponseEntity.ok(informationBMIUser);
        Assert.assertEquals(expected, BMIService.getInformationBMIByUser("namnd"));
    }

}
