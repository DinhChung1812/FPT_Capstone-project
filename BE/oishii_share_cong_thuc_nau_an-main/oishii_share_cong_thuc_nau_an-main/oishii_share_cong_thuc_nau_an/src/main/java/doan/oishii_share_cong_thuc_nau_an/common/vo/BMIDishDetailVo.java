package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//@NamedNativeQuery(query = "select d.dish_id as dishID, cast(cast(a.totalStartRate as float)/a.numberStartRateInDish as decimal(10,2)) as avgStarRate , " +
//        "d.name as dishName,d.origin,d.calo as totalCalo, d.level, d.number_people_for_dish as numberPeopleForDish,d.size, CONVERT(varchar,d.create_date) as createDate, d.video_url as video, dc.name as dishCate, " +
//        "f.formula_id as formulaID,f.describe as formulaDescribe,ac.username as verifier,f.summary, d.time " +
//        "from Dish d " +
//        "left join (select dc.dish_id, SUM(dc.start_rate) as totalStartRate,COUNT(dc.dish_id) as numberStartRateInDish  from dish_comment dc " +
//        "where GETDATE()-cast(dc.Update_Date as datetime)<= 5 and dc.status <> 3 group by dc.dish_id) a " +
//        "on a.dish_id = d.dish_id " +
//        "join dish_dish_category ddc on d.dish_id = ddc.dish_id " +
//        "join dish_category dc on ddc.dish_categoryid = dc.dish_category_id " +
//        "join Formula f on f.formula_id = d.formula_id " +
//        "join Account ac on ac.account_id = f.account_id " +
//        "where d.status = 1 and d.calo >= 0 and d.calo <= :calo and dc.name = :meal " +
//        "order by cast(a.totalStartRate as float)/a.numberStartRateInDish desc", name = "BMIDishDetailVoQuery", resultSetMapping = "BMIDishVoResult")
//
//@SqlResultSetMapping(name = "BMIDishVoResult", classes = {
//        @ConstructorResult(targetClass = BMIDishDetailVo.class,
//                columns = {@ColumnResult(name = "dishId", type = Integer.class), @ColumnResult(name = "avgStarRate", type = Double.class),
//                        @ColumnResult(name = "name", type = String.class), @ColumnResult(name = "origin", type = String.class),
//                        @ColumnResult(name = "calo", type = Integer.class), @ColumnResult(name = "level", type = Integer.class), @ColumnResult(name = "numberPeopleForDish", type = Integer.class),
//                        @ColumnResult(name = "size", type = Integer.class), @ColumnResult(name = "createDate", type = String.class),
//                        @ColumnResult(name = "video", type = String.class), @ColumnResult(name = "dishCate", type = String.class),
//                        @ColumnResult(name = "formulaID", type = Integer.class), @ColumnResult(name = "describe", type = String.class),
//                        @ColumnResult(name = "userName", type = String.class), @ColumnResult(name = "summary", type = String.class), @ColumnResult(name = "time", type = Integer.class)
//                })
//})
//@Data

@Getter
@Setter

public class BMIDishDetailVo {

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

    private String dishCate;

    private String ingredientA;

    private String ingredientB;

    private Integer totalStarRate;

    private Integer numberStartRateInDish;

    private Double avgStartRate;

    private String nameIngredient;

    private Double totalCaloBreak;

    private Double totalCaloLunch;

    private Double totalCaloDinner;

    private Double totalRemainingCalo;


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
    private Long id;

    public BMIDishDetailVo() {
    }

    public BMIDishDetailVo(Integer dishID, String dishName, String origin, Integer totalCalo, Integer level, Integer numberPeopleForDish, Integer size, String createDate, String video,
                           String dishCate, Integer formulaID, String formulaDescribe, String verifier, String summary, Integer time) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.origin = origin;
        this.totalCalo = totalCalo;
        this.level = level;
        this.numberPeopleForDish = numberPeopleForDish;
        this.size = size;
        this.createDate = createDate;
        this.video = video;
        this.dishCate = dishCate;
        this.formulaID = formulaID;
        this.formulaDescribe = formulaDescribe;
        this.verifier = verifier;
        this.summary = summary;
        this.time = time;
    }

    public BMIDishDetailVo(Integer dishID, String dishName, String origin, Integer totalCalo, Integer level, Integer numberPeopleForDish, Integer size, String createDate, String video,
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

    public BMIDishDetailVo( String ingredientA, String ingredientB) {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
    }

    public BMIDishDetailVo(String nameIngredient) {
        this.nameIngredient = nameIngredient;
    }

    public BMIDishDetailVo(Integer totalStarRate) {
        this.totalStarRate = totalStarRate;
    }

    public BMIDishDetailVo(Integer dishID, String dishName, String origin, Integer totalCalo, Integer level, Integer numberPeopleForDish, Integer size, String createDate, String video, String dishCate, String ingredientA, String ingredientB, Integer totalStarRate, Integer numberStartRateInDish, Double avgStartRate, String nameIngredient, Double totalCaloBreak, Double totalCaloLunch, Double totalCaloDinner, Double totalRemainingCalo, Integer formulaID, String formulaDescribe, String verifier, String summary, Integer time, List<StepVo> stepList, List<DishImageVo> dishImageList, List<IngredientDetailVo> ingredientDetailList, Long id) {

        this.dishID = dishID;
        this.dishName = dishName;
        this.origin = origin;
        this.totalCalo = totalCalo;
        this.level = level;
        this.numberPeopleForDish = numberPeopleForDish;
        this.size = size;
        this.createDate = createDate;
        this.video = video;
        this.dishCate = dishCate;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.totalStarRate = totalStarRate;
        this.numberStartRateInDish = numberStartRateInDish;
        this.avgStartRate = avgStartRate;
        this.nameIngredient = nameIngredient;
        this.totalCaloBreak = totalCaloBreak;
        this.totalCaloLunch = totalCaloLunch;
        this.totalCaloDinner = totalCaloDinner;
        this.totalRemainingCalo = totalRemainingCalo;
        this.formulaID = formulaID;
        this.formulaDescribe = formulaDescribe;
        this.verifier = verifier;
        this.summary = summary;
        this.time = time;
        this.stepList = stepList;
        this.dishImageList = dishImageList;
        this.ingredientDetailList = ingredientDetailList;
        this.id = id;
    }
}
