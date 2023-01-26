package doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish;

import doan.oishii_share_cong_thuc_nau_an.entities.Dish;
import lombok.Data;

import java.util.Set;

@Data
public class DishCategoryRequest {
    private Integer dishCategoryID;
    private String name;
    private Set<Dish> idDish;
}
