package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class DishFormulaVo {

    private Integer dishID;

    private String dishName;

    private String createDate;

    private Integer formulaID;

    private String formulaDescribe;

    private String verifier;

    private String summary;


    public DishFormulaVo(Integer dishID, String dishName, String createDate, Integer formulaID, String formulaDescribe, String verifier, String summary) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.createDate = createDate;
        this.formulaID = formulaID;
        this.formulaDescribe = formulaDescribe;
        this.verifier = verifier;
        this.summary = summary;

    }

}
