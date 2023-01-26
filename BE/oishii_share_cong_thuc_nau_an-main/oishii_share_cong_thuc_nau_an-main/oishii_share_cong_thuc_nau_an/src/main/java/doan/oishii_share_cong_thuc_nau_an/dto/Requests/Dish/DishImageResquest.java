package doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish;

import lombok.Data;

@Data
public class DishImageResquest {
    private Integer imageId;

    private String note;

    private String url;

    private Integer dishId;
}
