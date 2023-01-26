package doan.oishii_share_cong_thuc_nau_an.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Dish" )
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "dishID"
)
public class Dish  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Integer dishID;

    @Column(name = "name", columnDefinition = "nvarchar(max)")
    private String name;

    @Column(name = "origin", columnDefinition = "nvarchar(max)")
    private String origin;

    @Column(name = "calo")
    private Integer calo;

    @Column(name = "level")
    private Integer level;

    @Column(name = "number_people_for_dish")
    private Integer numberPeopleForDish;

    @Column(name = "size")
    private Integer size;

    @Column(name = "time")
    private Integer time;

    @Column(name = "video_url", columnDefinition = "nvarchar(max)")
    private String video;

    @Column(name = "create_date", columnDefinition = "DATE")
    private LocalDate createDate;

    @Column(name = "status")
    private Integer status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "formula_id", referencedColumnName = "formula_id")
//    @JsonManagedReference(value = "dish-fomula")
    private Formula formulaId;

    @OneToMany( mappedBy = "dishID",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "image-dish")
    private List<DishImage> listDishImage;

    @OneToMany( mappedBy = "dishID",fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "comment-dish")
    private List<DishComment> listDishComment;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Dish_DishCategory", joinColumns = @JoinColumn(name = "DishId"),
            inverseJoinColumns = @JoinColumn(name = "DishCategoryID"))
//    @JsonManagedReference(value = "category-dish")
    private List<DishCategory> idDishCategory;

    @OneToMany( mappedBy = "dishID",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "ingredient-dish")
    private List<IngredientDetail> listIngredientDetail;
}
