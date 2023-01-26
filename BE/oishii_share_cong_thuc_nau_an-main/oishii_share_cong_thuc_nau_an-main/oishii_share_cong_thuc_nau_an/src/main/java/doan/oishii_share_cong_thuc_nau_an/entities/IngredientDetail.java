package doan.oishii_share_cong_thuc_nau_an.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "IngredientDetail" )
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "ingredientDetailID"
//)
public class IngredientDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_detail_id")
    private Integer ingredientDetailID;

    @Column(name = "name", columnDefinition = "nvarchar(max)")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit", columnDefinition = "nvarchar(max)")
    private String unit;

    @Column(name = "calo",nullable=true)
    private Integer calo;

    @Column(name = "main_ingredient")
    private Integer mainIngredient;

//    @ManyToOne
//    @JoinColumn(name = "ingredient_id", referencedColumnName = "ingredient_id", nullable = false)
////    @JsonBackReference
//    private Ingredient ingredientID;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "dish_id", nullable = false)
//    @JsonBackReference(value = "ingredient-dish")
    private Dish dishID;

//    @OneToOne(mappedBy = "ingredientDetail")
////    @JsonBackReference
//    private IngredientChange IngredientChange;


    @OneToMany( mappedBy = "ingredientDetail",fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<IngredientChange> ingredientChangeList;
}
