package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public class SaveDishCommentRequest {
    private Integer dishId;
    private Integer dishCommentId;
    private String content;
    private Integer starRate;
}
