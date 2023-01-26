package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "old Password cannot blank")
    @NotNull(message = "old Password cannot null")
    private String oldPassword;

    @NotBlank(message = "new Password cannot blank")
    @NotNull(message = "new Password cannot null")
    private String newPassword;
}
