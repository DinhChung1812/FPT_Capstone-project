package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;

import javax.persistence.Column;
@Data
public class SaveCategoryRequest {

    private Integer categoryId;
    private String categoryName;
    private String categoryImage;

}
