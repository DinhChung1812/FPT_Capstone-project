package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientConflictVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IngredientConflictService {

    Page<IngredientConflictVo> getListIngredientConflict (String searchData, Integer pageIndex, Integer pageSize);

    IngredientConflictVo getIngredientConflictInfo(Integer ingredientConflictId);

    ResponseEntity<?> createIngredientConflict(String ingredientA, String ingredientB, String consequence, String userName);

    ResponseEntity<?> updateIngredientConflict(Integer ingredientConflictId,String ingredientA,String ingredientB, String consequence, String userName);

    ResponseEntity<?> deleteIngredientConflict(Integer blogId);
}
