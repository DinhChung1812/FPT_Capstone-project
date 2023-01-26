package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.*;

import javax.persistence.Column;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StepVo {

    private Integer stepID;

    private String describe;

    private Integer title;
}
