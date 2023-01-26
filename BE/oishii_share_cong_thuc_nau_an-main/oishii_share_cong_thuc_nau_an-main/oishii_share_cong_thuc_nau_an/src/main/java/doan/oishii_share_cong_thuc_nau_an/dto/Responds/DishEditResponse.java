package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DishEditResponse {
    private Integer dishID;
    private String name;
    private String origin;
    private Integer calo;
    private Integer level;
    private Integer numberPeopleForDish;
    private Integer size;
    private Integer time;
    private String video;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate createDate;
    private Integer status;
    private FormulaResponse formula;
    private List<StepResponse> listStep;
    private List<DishImageResponse> listDishImage;
    private List<DishCategoryResponse> listDishCategory;
    private List<IngredientDetailResponse> listIngredientDetail;
}
