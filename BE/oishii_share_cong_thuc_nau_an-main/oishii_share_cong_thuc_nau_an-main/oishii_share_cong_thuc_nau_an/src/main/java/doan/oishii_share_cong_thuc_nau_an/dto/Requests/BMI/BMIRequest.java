package doan.oishii_share_cong_thuc_nau_an.dto.Requests.BMI;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BMIRequest {
    private String username;
    private String gender;
    private String target;
    private Double targetIndex;
    private Double high;
    private Double weight;
    private Double r;
    private LocalDate dob;
}
