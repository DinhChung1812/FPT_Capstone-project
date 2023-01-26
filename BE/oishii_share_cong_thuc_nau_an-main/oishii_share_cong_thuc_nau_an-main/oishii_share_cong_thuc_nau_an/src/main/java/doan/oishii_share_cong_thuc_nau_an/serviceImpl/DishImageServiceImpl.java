package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.DishImageRepository;
import doan.oishii_share_cong_thuc_nau_an.service.DishImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishImageServiceImpl implements DishImageService {
    @Autowired
    private DishImageRepository dishImageRepository;
    @Override
    public List<DishImageVo> findByDishID(Integer dishId) {
        return dishImageRepository.findByDishID(dishId);
    }
}
