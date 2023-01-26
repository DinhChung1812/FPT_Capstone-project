//package doan.oishii_share_cong_thuc_nau_an.web.entities;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "IngredientCategory" )
//@Data
//public class IngredientCategory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ingredient_cate_id")
//    private Integer ingredientCateID;
//
//    @Column(name = "name", columnDefinition = "nvarchar(max)")
//    private String name;
//
//    @OneToMany( mappedBy = "ingredientCategoryID", cascade = CascadeType.ALL)
////    @JsonManagedReference
//    private Set<Ingredient> listIngredient;
//}
