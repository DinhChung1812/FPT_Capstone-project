//package doan.oishii_share_cong_thuc_nau_an.web.entities;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Table(name = "Ingredient" )
//@Data
//public class Ingredient {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ingredient_id")
//    private Integer ingredientID;
//
//    @Column(name = "name", columnDefinition = "nvarchar(max)")
//    private String name;
//
//    @Column(name = "calo",nullable=true)
//    private Integer calo;
//
//    @Column(name = "ingredient_price",nullable=true)
//    private Double ingredientPrice;
//
//    @Column(name = "delete_flg", nullable=true)
//    private Integer deleteFlag;
//
//    @ManyToOne
//    @JoinColumn(name = "ingredient_cate_id", referencedColumnName = "ingredient_cate_id", nullable = false)
////    @JsonBackReference
//    private IngredientCategory ingredientCategoryID;
//
//    @ManyToOne
//    @JoinColumn(name = "ingredient_unit_id", referencedColumnName = "ingredient_unit_id", nullable = false)
////    @JsonBackReference
//    private IngredientUnit ingredientUnitID;
//
//    @OneToMany( mappedBy = "ingredientID", cascade = CascadeType.ALL)
////    @JsonManagedReference
//    private Set<IngredientDetail> listIngredientDetail;
//
//    @OneToMany(mappedBy = "ingredientID", cascade = CascadeType.ALL)
////    @JsonBackReference
//    private Set<IngredientChange> listIngredientChange;
//
//    public Ingredient(Integer ingredientID, String name, Integer calo, Double ingredientPrice) {
//        this.ingredientID = ingredientID;
//        this.name = name;
//        this.calo = calo;
//        this.ingredientPrice = ingredientPrice;
//
//    }
//
//    public Ingredient() {
//
//    }
//}
