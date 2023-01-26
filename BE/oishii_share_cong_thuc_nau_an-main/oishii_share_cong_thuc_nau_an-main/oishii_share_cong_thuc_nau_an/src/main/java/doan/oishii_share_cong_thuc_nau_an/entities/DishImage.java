package doan.oishii_share_cong_thuc_nau_an.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "DishImage" )
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "dishImageID"
//)
public class DishImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_image_id")
    private Integer dishImageID;

    @Column(nullable=true, name = "url", columnDefinition = "nvarchar(max)" )
    private String url;

    @Column(name = "note", columnDefinition = "nvarchar(max)")
    private String note;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "dish_id", nullable = false)
//    @JsonBackReference(value = "image-dish")
    private Dish dishID;
}
