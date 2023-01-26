package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class SaveBlogRequest {
    private Integer blogId;
    private String title;
    private String content;
}
