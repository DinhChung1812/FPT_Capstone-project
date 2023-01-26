package doan.oishii_share_cong_thuc_nau_an.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Formula" )
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "formulaID"
)
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "formula_id")
    private Integer formulaID;

    @Column(nullable=true, name = "describe", columnDefinition = "nvarchar(max)")
    private String describe;

//    @Column(nullable=true, name = "verifier", columnDefinition = "nvarchar(max)")
//    private String verifier;

    @Column(nullable=true, name = "summary", columnDefinition = "nvarchar(max)")
    private String summary;

    @OneToMany( mappedBy = "formulaID", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<Step> listStep;

    @OneToOne(mappedBy = "formulaId")
//    @JsonBackReference(value = "dish-fomula")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @OneToMany( mappedBy = "formula",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FavouriteRecipe> favouriteRecipes;
}
