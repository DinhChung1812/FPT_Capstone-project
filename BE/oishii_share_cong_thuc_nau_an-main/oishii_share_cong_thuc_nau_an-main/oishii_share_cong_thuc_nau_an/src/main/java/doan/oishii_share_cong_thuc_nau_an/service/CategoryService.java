package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    public List<DishCategoryVo> findAllCategory();

    Page<DishCategoryVo> getListCategory(String searchData, Integer pageIndex, Integer pageSize);

    ResponseEntity<?> getCategoryInfo(Integer categoryId);

    ResponseEntity<?> createCategory(String categoryName,String categoryImage);

    ResponseEntity<?> updateCategory(Integer categoryId,String categoryName,String categoryImage);

    ResponseEntity<?> deleteCategory(Integer categoryId);
}
