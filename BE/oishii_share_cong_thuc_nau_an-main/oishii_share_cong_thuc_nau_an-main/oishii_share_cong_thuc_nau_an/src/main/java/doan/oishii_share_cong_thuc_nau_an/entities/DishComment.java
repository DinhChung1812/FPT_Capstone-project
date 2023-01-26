package doan.oishii_share_cong_thuc_nau_an.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "DishComment" )
@Data
public class DishComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_comment_id")
    private Integer dishCommentID;

    @Column(nullable=true, name = "content", columnDefinition = "nvarchar(max)")
    private String content;

    @Column(nullable=true, name = "flag")
    private Integer flag;

    @Column(name = "status")
    private Integer status;

    @Column(name = "start_rate")
    private Integer startRate;

    @Column(name = "total_like")
    private Integer totalLike;

    @Column(name = "total_disLike")
    private Integer totalDisLike;

    @Column(name = "create_date", columnDefinition = "DATE")
    private LocalDate createDate;

    @Column(name = "update_Date", columnDefinition = "DATE")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "dish_id", nullable = false)
//    @JsonBackReference(value = "comment-dish")
    private Dish dishID;

//    @ManyToMany (mappedBy = "idDishComment")
//    @JsonBackReference
//    private Set<Account> idAccount;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
//    @JsonBackReference
    private Account account;

    @OneToMany( mappedBy = "dishComment",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CheckLikeDislikeReport> checkLikeOrDislikes;


}
