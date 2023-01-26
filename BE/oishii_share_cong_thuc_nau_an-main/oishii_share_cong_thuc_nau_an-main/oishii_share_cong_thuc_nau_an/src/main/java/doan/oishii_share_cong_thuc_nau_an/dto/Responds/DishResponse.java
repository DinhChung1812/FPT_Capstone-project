package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import com.fasterxml.jackson.annotation.JsonFormat;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish.DishImageResquest;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DishResponse {

    private Integer dishID;

    private String name;

    private Integer Level;

    private Integer size;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate createDate;

    private int quantityRate;

    private double avgStarRate;

    private Integer time;

    private String verifier;

    private String describe;

    private String image;

}
