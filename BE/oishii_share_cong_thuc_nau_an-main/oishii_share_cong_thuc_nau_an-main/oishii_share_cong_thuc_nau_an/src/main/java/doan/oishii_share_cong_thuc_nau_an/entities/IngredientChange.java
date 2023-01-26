package doan.oishii_share_cong_thuc_nau_an.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "IngredientChange" )
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "ingredientChangeID"
)
public class IngredientChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_change_id")
    private Integer ingredientChangeID;

    @Column(name = "name", columnDefinition = "nvarchar(max)")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit", columnDefinition = "nvarchar(max)")
    private String unit;

    @Column(name = "calo",nullable=true)
    private Integer calo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_detail_id", referencedColumnName = "ingredient_detail_id")
    private IngredientDetail ingredientDetail;

//    @OneToOne
//    @JoinColumn(name = "ingredient_detail_id", referencedColumnName = "ingredient_detail_id")
////    @JsonManagedReference
//    private IngredientDetail ingredientDetail;

}
