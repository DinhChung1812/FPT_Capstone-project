package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientChangeVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientDetailVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.IngredientChangeRepository;
import doan.oishii_share_cong_thuc_nau_an.repositories.IngredientDetailRepository;
import doan.oishii_share_cong_thuc_nau_an.service.IngredientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientDetailServiceImpl implements IngredientDetailService {

    @Autowired
    private IngredientDetailRepository ingredientDetailRepository;

    @Autowired
    private IngredientChangeRepository ingredientChangeRepository;

    @Override
    public List<IngredientDetailVo> findIngredientDetailVoByDishID(Integer dishId) {
        List<IngredientDetailVo> ingredientDetailVos = ingredientDetailRepository.findIngredientDetailVoByDishID(dishId);
        for(IngredientDetailVo ingredientDetailVo : ingredientDetailVos){
             List<IngredientChangeVo> ingredientChangeVoList = ingredientChangeRepository.getIngredientChange(ingredientDetailVo.getIngredientDetailId());
             ingredientDetailVo.setIngredientChangeVoList(ingredientChangeVoList);
        }
        return ingredientDetailVos;
    }

//    @Override
//    public List<IngredientChangeVo> getIngredientChange(List<Integer> ingredientIds) {
//        List<IngredientChangeVo> ingredientChangeVoList = new ArrayList<>();
//        for (Integer ingredientId : ingredientIds) {
//            IngredientChangeVo ingredientChangeVo = ingredientChangeRepository.getIngredientChange(ingredientId);
//            if (ingredientChangeVo == null) {
//                IngredientChangeVo ingredientChangeVoNotFound = new IngredientChangeVo();
//                ingredientChangeVoNotFound.setIngredientDetailId(ingredientId);
//                ingredientChangeVoNotFound.setNotFoundMessage("Không tìm thấy nguyên liệu thay thế cho nguyên liệu này");
//                ingredientChangeVoList.add(ingredientChangeVoNotFound);
//            } else {
//                ingredientChangeVo.setIngredientDetailId(ingredientId);
//                ingredientChangeVoList.add(ingredientChangeVo);
//            }
//        }
//        return ingredientChangeVoList;
//    }
}
