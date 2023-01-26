package doan.oishii_share_cong_thuc_nau_an.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "IngredientConflict" )
@Data
public class IngredientConflict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_conflict_id")
    private Integer ingredientConflictId;

    @Column(name = "ingredient_a", columnDefinition = "nvarchar(max)")
    private String ingredientA;

    @Column(name = "ingredient_b", columnDefinition = "nvarchar(max)")
    private String ingredientB;

    @Column(name = "consequence", columnDefinition = "nvarchar(max)")
    private String consequence;

    @Column(name = "create_username", columnDefinition = "nvarchar(max)")
    private String createUsername;

    @Column(name = "create_date", columnDefinition = "DATE")
    private LocalDate createDate;

    @Column(name = "update_username", columnDefinition = "nvarchar(max)")
    private String updateUsername;

    @Column(name = "update_date", columnDefinition = "DATE")
    private LocalDate updateDate;



}
