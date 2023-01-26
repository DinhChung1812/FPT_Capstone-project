package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@NamedNativeQuery(query = "select top 5 d.dish_id as dishId, a.totalStartRate, a.numberStartRateInDish," +
        " d.level, d.calo, d.name, d.number_people_for_dish as numberPeople, d.formula_id as formulaId, f.describe, f.summary," +
        " cast(cast(a.totalStartRate as float)/a.numberStartRateInDish as decimal(10,2)) as avgStarRate , CONVERT(varchar,d.create_date) as createDate," +
        "d.time, ac.username  " +
        "from Dish d left join  (select dc.dish_id, SUM(dc.start_rate) as totalStartRate,COUNT(dc.dish_id) as numberStartRateInDish  from dish_comment dc " +
        "where GETDATE()-cast(dc.Update_Date as datetime)<= :numberDay and dc.status <> 3 group by dc.dish_id) a " +
        "on a.dish_id = d.dish_id " +
        "join Formula f on f.formula_id = d.formula_id " +
        "join Account ac on ac.account_id = f.account_id " +
        " where d.status <>3 and ac.status <> 3" +
        "order by cast(a.totalStartRate as float)/a.numberStartRateInDish desc, d.create_date desc" ,  name ="DishVoQuery", resultSetMapping="DishVoResult")
@SqlResultSetMapping(name="DishVoResult", classes = {
        @ConstructorResult(targetClass = DishVo.class,
                columns = {@ColumnResult(name="dishId",type = Integer.class), @ColumnResult(name="totalStartRate",type = Integer.class),
                        @ColumnResult(name="numberStartRateInDish",type = Integer.class),@ColumnResult(name="level",type = Integer.class),@ColumnResult(name="calo",type = Integer.class),
                        @ColumnResult(name="name",type = String.class),@ColumnResult(name="numberPeople",type = Integer.class),@ColumnResult(name="formulaId",type = Integer.class),
                        @ColumnResult(name="describe",type = String.class), @ColumnResult(name="summary",type = String.class),
                        @ColumnResult(name="avgStarRate",type = Double.class), @ColumnResult(name="createDate",type = String.class),
                        @ColumnResult(name="time",type = Integer.class),@ColumnResult(name="username",type = String.class)
                })
})
@Data

@NoArgsConstructor
@Entity
public class DishVo {

    private Integer dishId;
    private Integer totalStarRate;
    private Integer numberStartRateInDish;
    private Integer level;
    private Integer calo;
    private String name;
    private Integer numberPeople;
    private Integer formulaId;
    private String describe;
    private String summary;

    private String createDate;

    private Double avgStarRate;

    private String urlImage;

    private Integer time;

    private String verifier;

    private String avatarImg;
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public DishVo(Integer dishId, Integer totalStarRate, Integer numberStartRateInDish, Integer level, Integer calo,
                  String name, Integer numberPeople, Integer formulaId, String describe, String summary, Double avgStarRate, String createDate,
                  Integer time, String verifier) {
        this.dishId = dishId;
        this.totalStarRate = totalStarRate;
        this.numberStartRateInDish = numberStartRateInDish;
        this.level = level;
        this.calo = calo;
        this.name = name;
        this.numberPeople = numberPeople;
        this.formulaId = formulaId;
        this.describe = describe;
        this.summary = summary;
        this.avgStarRate =avgStarRate;
        this.createDate = createDate;
        this.time = time;
        this.verifier =verifier;
    }

    public DishVo(Integer dishId,  Integer level, Integer calo,
                  String name, Integer numberPeople, Integer formulaId, String describe, String summary, String createDate,
                  Integer time, String verifier, String avatarImg) {
        this.dishId = dishId;
        this.level = level;
        this.calo = calo;
        this.name = name;
        this.numberPeople = numberPeople;
        this.formulaId = formulaId;
        this.describe = describe;
        this.summary = summary;
        this.createDate = createDate;
        this.time = time;
        this.verifier =verifier;
        this.avatarImg = avatarImg;
    }
}
