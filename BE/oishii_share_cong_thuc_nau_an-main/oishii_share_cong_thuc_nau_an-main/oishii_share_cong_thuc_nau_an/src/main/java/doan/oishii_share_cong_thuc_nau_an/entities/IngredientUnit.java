//package doan.oishii_share_cong_thuc_nau_an.web.entities;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "IngredientUnit" )
//@Data
//public class IngredientUnit {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ingredient_unit_id")
//    private Integer ingredientUnitID;
//
//    @Column(name = "name", columnDefinition = "nvarchar(max)")
//    private String name;
//
//    @OneToMany( mappedBy = "ingredientUnitID", cascade = CascadeType.ALL)
////    @JsonManagedReference
//    private Set<Ingredient> listIngredient;
//
//
//}
