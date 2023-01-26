package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.entities.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {


    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo" +
            "(d.dishID,d.name, cast(d.createDate as string) ,f.formulaID,f.describe,a.userName,f.summary)" +
            " from Dish d join d.formulaId f join f.account a " +
            "where d.status = 1 and a.status <> 3 and (cast(d.dishID as string ) LIKE :searchData " +
            "or d.name LIKE :searchData) order by d.dishID desc ")
    public Page<DishFormulaVo> findAllRecipe(@Param("searchData") String searchData, Pageable pageable);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo" +
            "(d.dishID,d.name, cast(d.createDate as string) ,f.formulaID,f.describe,a.userName,f.summary)" +
            " from Dish d join d.formulaId f join f.account a  join   d.idDishCategory c  " +
            "where d.status = 1 and a.status <> 3 and c.dishCategoryID = :categoryId  order by d.dishID desc ")
    public Page<DishFormulaVo> getRecipeByCategory(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo" +
            "(d.dishID,d.name, cast(d.createDate as string) ,f.formulaID,f.describe,a.userName,f.summary)" +
            " from Dish d join d.formulaId f join f.account a where a.userName = :creater " +
            "AND (cast(d.dishID as string ) LIKE :searchData " +
            "OR d.name LIKE :searchData) and d.status = 1 and a.status <> 3 order by d.dishID desc ")
    public Page<DishFormulaVo> getRecipeOfCreater(String creater, @Param("searchData") String searchData, Pageable pageable);


    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video, " +
            "f.formulaID,f.describe,a.userName,f.summary, d.time) " +
            "from Dish d join d.formulaId f join f.account a where  d.dishID = :dishID")
    public DishDetailVo getDishDetail(Integer dishID);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video , " +
            "f.formulaID,f.describe,a.userName,f.summary, d.time) from Dish d join d.formulaId f join f.account a where  d.dishID = :dishID")
    public DishDetailVo getDishDetail2(Integer dishID);

    @Query(value = "(select distinct d.*, ft.rank r1 from Dish d left join ingredient_detail id on d.dish_id=id.dish_id left join freetexttable(dish,name,:name) ft on d.dish_id = ft.[key]\n" +
            "            left join freetexttable (ingredient_detail,name,:ingredient) fti on id.ingredient_detail_id = fti.[key]\n" +
            "            and (d.status=1 and id.main_ingredient = 1)where ft.rank>45 and ft.rank<=200) order by ft.rank desc", nativeQuery = true)
    public List<Dish> findDishByNameOrMainIngredientLike(String name, String ingredient, Pageable pageable);

    @Query(value = "select d.* from dish d left join dish_dish_category ddc on d.dish_id = ddc.dish_id\n" +
            "\t\t\t\t\t join dish_category dc on ddc.dish_categoryid = dc.dish_category_id\n" +
            "\t\t\t\t\t where dc.dish_category_id = :id and d.status=1", nativeQuery = true)
    public List<Dish> findDishByDishCategory(Integer id, Pageable pageable);

    @Query(value = "select distinct top 5 * from dish d where d.status=1 order by d.create_date desc, d.dish_Id desc ", nativeQuery = true)
    public List<Dish> getTop5ByNew();

    @Query(value = "insert into dish_dish_category(dish_id,dish_categoryid)\n" +
            "values (:dishId,:cateId)", nativeQuery = true)
    @Modifying
    @Transactional
    public void insertDishCategory(@Param("dishId") int dishId, @Param("cateId") int cateId);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video, dc.name, " +
            "f.formulaID,f.describe,a.userName,f.summary, d.time) " +
            "from Dish d join d.listIngredientDetail lid " +
            "join d.idDishCategory dc " +
            "join d.formulaId f join f.account a " +
            "where d.status = 1 and dc.name = :meal and lid.name = :mainIngredient and d.calo >= 0 and d.calo <= :calo")
    public List<BMIDishDetailVo> getDishByBMIUser(String meal, String mainIngredient, Integer calo);

    @Query("select d from Dish d where d.status=1 and d.dishID = :id")
    public Dish findByDishID(Integer id);

    @Query(value = "UPDATE dbo.dish \n" +
            "SET name = :name,calo = :calo, level= :level, number_people_for_dish= :number_people_for_dish,size= :size,time= :time,video_url= :video_url \n" +
            "WHERE dish_id = :dish_id", nativeQuery = true)
    @Modifying
    public void updateRecipe(String name, Integer calo, Integer level, Integer number_people_for_dish, Integer size, Integer time, String video_url, Integer dish_id);

    @Query(value = "UPDATE dish SET status = 3 WHERE dish_id= :recipeId", nativeQuery = true)
    @Modifying
    public void deleteRecipe(Integer recipeId);

    public Boolean existsDishByNameAndStatus(String name, Integer status);

    public boolean existsDishByDishIDAndStatus(Integer id, Integer status);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video, dc.name, " +
            "f.formulaID,f.describe,a.userName,f.summary, d.time) " +
            "from Dish d " +
            "join d.idDishCategory dc " +
            "join d.formulaId f join f.account a " +
            "where d.status = 1 and dc.name = :meal and d.calo >= 0 and d.calo <= :calo")
    public List<BMIDishDetailVo> getDishMCByBMIUser(String meal, Integer calo);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video," +
            "f.formulaID,f.describe,a.userName,f.summary, d.time) " +
            "from Dish d " +
            "join d.formulaId f join f.account a " +
            "where d.dishID = :dishID")
    public BMIDishDetailVo getDishBMIDetail(Integer dishID);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo(" +
            "ic.ingredientA , ic.ingredientB )" +
            "from IngredientConflict ic ")
    public List<BMIDishDetailVo> getIngredientConflict();

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo(" +
            "lid.name) " +
            "from Dish d join d.listIngredientDetail lid " +
            "where d.status = 1 and d.dishID = :dishID")
    public List<BMIDishDetailVo> getIngredientByDishID(Integer dishID);

    @Query("select " +
            "cast(SUM(dc.startRate) as integer)" +
            "from DishComment dc " +
            "where dc.dishID.dishID = :dishID " +
            "group by dc.dishID ")
    public Integer getStartRateByDishID(Integer dishID);

    @Query("select " +
            "cast(COUNT(dc.dishID) as integer) " +
            "from DishComment dc " +
            "where dc.dishID.dishID = :dishID " +
            "group by dc.dishID ")
    public Integer getNumberStartRateByDishID(Integer dishID);

    @Query(value = "select count(distinct d.dish_id) from Dish d left join ingredient_detail id on d.dish_id=id.dish_id left join freetexttable(dish,name,:name) ft on d.dish_id = ft.[key]  \n" +
            "left join freetexttable (ingredient_detail,name,:ingredient) fti on id.ingredient_detail_id = fti.[key]\n" +
            "and (d.status=1 and id.main_ingredient = 1) where ft.rank>45 and ft.rank<=200", nativeQuery = true)
    int totalPage(String name, String ingredient);

    @Query(value = "select count(distinct(d.dish_id)) from dish d left join dish_dish_category ddc on d.dish_id = ddc.dish_id join dish_category dc on ddc.dish_categoryid = dc.dish_category_id where dc.dish_category_id = :id and d.status=1", nativeQuery = true)
    int totalPageSeacrchByCate(Integer id);
}
