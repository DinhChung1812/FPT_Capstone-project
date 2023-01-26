package doan.oishii_share_cong_thuc_nau_an.common.vo;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DishDetailVo {

    //dish ----------------------------

    private Integer dishID;

    private String dishName;

    private String origin;

    private Integer totalCalo;

    private Integer level;

    private Integer numberPeopleForDish;

    private Integer size;

    private String createDate;

    private String video;


    //formula-----------------------------------

    private Integer formulaID;

    private String formulaDescribe;

    private String verifier;

    private String summary;

    private Integer time;

    //step--------------------------------

    private List<StepVo> stepList;

    //DishImage------------------------------
    private List<DishImageVo> dishImageList;

    //DishComment------------------------------

    //private List<DishCommentAccountVo> dishCommentList;

    //IngredientDetail---------------------
    private List<IngredientDetailVo> ingredientDetailList;

    public DishDetailVo() {
    }

    public DishDetailVo(Integer dishID, String dishName, String origin, Integer totalCalo, Integer level, Integer numberPeopleForDish, Integer size, String createDate, String video,
                        Integer formulaID, String formulaDescribe, String verifier, String summary, Integer time) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.origin = origin;
        this.totalCalo = totalCalo;
        this.level = level;
        this.numberPeopleForDish = numberPeopleForDish;
        this.size = size;
        this.createDate = createDate;
        this.video = video;
        this.formulaID = formulaID;
        this.formulaDescribe = formulaDescribe;
        this.verifier = verifier;
        this.summary = summary;
        this.time = time;
    }
}
