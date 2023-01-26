package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

@NoArgsConstructor
public class DishCommentAccountVo {

    private Integer dishCommentID;

    private String content;

    private Integer flag;

    private Integer startRate;

    private Integer totalLike;

    private Integer totalDisLike;

    private String createDate;

    private String updateDate;

    private String accountUserName;

    private String avatarImage;

    private Integer checkLike;

    private Integer checkDislike;

    private Integer checkEdit;

    public DishCommentAccountVo(Integer dishCommentID,
          String content, Integer flag, Integer startRate, Integer totalLike, Integer totalDisLike, String createDate, String updateDate
            , String accountUserName,String avatarImage) {
        this.dishCommentID = dishCommentID;
        this.content = content;
        this.flag = flag;
        this.startRate = startRate;
        this.totalLike = totalLike;
        this.totalDisLike = totalDisLike;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.accountUserName = accountUserName;
        this.avatarImage = avatarImage;
    }
}
