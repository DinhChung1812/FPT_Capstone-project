package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientConflictVo {

    private Integer ingredientConflictId;

    private String ingredientA;

    private String ingredientB;

    private String consequence;

    private String createUsername;

    private LocalDate createDate;

    private String updateUsername;

    private LocalDate updateDate;

}
