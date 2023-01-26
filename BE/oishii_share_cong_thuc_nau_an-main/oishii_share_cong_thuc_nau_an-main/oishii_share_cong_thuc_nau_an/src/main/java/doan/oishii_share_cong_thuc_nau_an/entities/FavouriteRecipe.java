package doan.oishii_share_cong_thuc_nau_an.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "FavouriteRecipe" )
@Data
public class FavouriteRecipe {
    @EmbeddedId
    private CheckLikeDislikeReportId checkId = new CheckLikeDislikeReportId();

    @ManyToOne
    @MapsId(value = "accountId")
    private Account account;

    @ManyToOne
    @MapsId(value = "dishCommentId")
    private Formula formula;
}
