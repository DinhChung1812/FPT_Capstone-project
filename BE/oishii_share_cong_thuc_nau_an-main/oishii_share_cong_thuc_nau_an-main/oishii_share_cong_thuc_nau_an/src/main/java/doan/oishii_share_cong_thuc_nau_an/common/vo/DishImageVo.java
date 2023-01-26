package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishImageVo {

    private Integer dishImageID;

    private String url;

    private String note;

}
