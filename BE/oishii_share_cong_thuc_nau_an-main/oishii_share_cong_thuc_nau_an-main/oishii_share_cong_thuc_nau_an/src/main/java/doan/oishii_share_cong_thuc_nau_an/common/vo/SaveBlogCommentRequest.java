package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;

@Data
public class SaveBlogCommentRequest {

    private Integer blogId;
    private Integer blogCommentId;
    private String content;
}
