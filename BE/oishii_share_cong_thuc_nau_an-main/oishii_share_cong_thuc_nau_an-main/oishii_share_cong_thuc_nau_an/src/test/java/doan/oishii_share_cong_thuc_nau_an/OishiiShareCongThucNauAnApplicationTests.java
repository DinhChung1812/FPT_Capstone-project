package doan.oishii_share_cong_thuc_nau_an;

import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishResponse;
import doan.oishii_share_cong_thuc_nau_an.serviceImpl.DishSeviceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class OishiShareCongThucNauAnApplicationTests {

    @Autowired
    DishSeviceImpl dishSevice;

    @Test
    void testSearchCate() {
        List<DishResponse> dishResponseList = (List<DishResponse>) dishSevice.getDishByCate(1,1);
        Assert.assertEquals(5,dishResponseList.size());
        Assert.assertEquals("trứng ốp la",dishResponseList.get(0).getName());
    }

//    @Test
//    void testSearchName() {
//        List<DishResponse> dishResponseList2 = dishSevice.getDishByName("c",1);
//        Assert.assertNotEquals(6,dishResponseList2.size());
//    }



}
