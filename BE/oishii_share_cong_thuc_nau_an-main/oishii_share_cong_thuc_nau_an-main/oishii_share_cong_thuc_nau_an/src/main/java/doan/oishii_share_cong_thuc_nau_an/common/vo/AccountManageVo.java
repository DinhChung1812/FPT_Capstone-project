package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountManageVo {
    private Integer accountId;
    private String userName;
    private String role;
    private String email;
    private String avatarImage;
    private String createDate;
}
