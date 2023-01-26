package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo;

import java.util.List;

public interface DishImageService {
     List<DishImageVo> findByDishID(Integer dishId);

}
