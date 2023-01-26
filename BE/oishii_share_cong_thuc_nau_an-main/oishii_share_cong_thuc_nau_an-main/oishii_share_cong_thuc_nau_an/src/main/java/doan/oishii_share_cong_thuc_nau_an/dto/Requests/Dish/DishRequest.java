package doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish;

import doan.oishii_share_cong_thuc_nau_an.entities.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DishRequest {
    private String name;

    private String origin;

    private Integer calo;

    private Integer Level;

    private Integer numberPeopleForDish;

    private Integer size;

    private Integer time;

    private String video;

    private LocalDate createDate;

    private Integer status;

    private Formula formula;

    private List<Step> listStep;

    private List<DishImage> listDishImage;

    private List<DishCategory> listDishCategory;

    private List<IngredientDetail> listIngredientDetail;

    private List<IngredientChange> listIngredientChange;
}
