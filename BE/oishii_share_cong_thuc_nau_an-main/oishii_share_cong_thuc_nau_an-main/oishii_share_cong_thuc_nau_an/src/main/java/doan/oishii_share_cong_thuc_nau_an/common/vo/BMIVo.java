package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Data
public class BMIVo {

    private String username;

    private String name;

    private LocalDate dob;

    private String gender;

    private Double high;

    private Double weight;

    private Double mobility;

    private Double totalCalo;

    private String target;

    private Double BMIIndex;

    private Double tagetIndex;

    private String BMIStatus;

    private String BMINote;

    public BMIVo(String username, String name, LocalDate dob, String gender, Double high, Double weight, Double mobility, Double totalCalo, String target, Double BMIIndex, String BMIStatus, String BMINote) {
        this.username = username;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.high = high;
        this.weight = weight;
        this.mobility = mobility;
        this.totalCalo = totalCalo;
        this.target = target;
        this.BMIIndex = BMIIndex;
        this.BMIStatus = BMIStatus;
        this.BMINote = BMINote;
    }
}
