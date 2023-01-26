package doan.oishii_share_cong_thuc_nau_an.common.utils;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SignupRequest {


    private String username;


    private String password;


    private String email;


    private String fullname;
}
