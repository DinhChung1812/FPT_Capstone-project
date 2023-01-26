package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.entities.FavouriteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRecipeRepository  extends JpaRepository<FavouriteRecipe, Integer> {
    @Modifying
    @Query( "delete from FavouriteRecipe f where f.account.accountId =:accountId and f.formula.formulaID =:formulaId")
    int removeFavouriteRecipe(Integer formulaId, Integer accountId);

    @Modifying
    @Query( "select f.formula.formulaID from FavouriteRecipe f where f.account.accountId =:accountId and f.formula.formulaID =:formulaId")
    List<Integer> checkFavouriteRecipe(Integer formulaId, Integer accountId);
}
