package doan.oishii_share_cong_thuc_nau_an.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "DishCategory" )
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "dishCategoryID"
//)
public class DishCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_category_id")
    private Integer dishCategoryID;

    @Column(nullable=true, name = "name", columnDefinition = "nvarchar(max)")
    private String name;

    @Column(name = "dish_category_image", columnDefinition = "nvarchar(max)")
    private String dishCategoryImage;

    @Column(name = "status")
    private Integer status;

    @ManyToMany (mappedBy = "idDishCategory",cascade = {CascadeType.MERGE})
//    @JsonBackReference(value = "category-dish")
    private Set<Dish> idDish;
}
