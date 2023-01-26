//package doan.oishii_share_cong_thuc_nau_an.service.impl;
//
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientCategoryVo;
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientUnitVo;
//import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientVo;
//import doan.oishii_share_cong_thuc_nau_an.common.vo.SaveIngredientRequest;
//import doan.oishii_share_cong_thuc_nau_an.repositories.*;
//import doan.oishii_share_cong_thuc_nau_an.service.IngredientService;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.Ingredient;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientCategory;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientChange;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientUnit;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class IngredientServiceImpl implements IngredientService {
//    @Autowired
//    private IngredientRepository ingredientRepository;
//
//    @Autowired
//    private IngredientCategoryRepository ingredientCategoryRepository;
//
//    @Autowired
//    private IngredientDetailRepository ingredientDetailRepository;
//
//    @Autowired
//    private IngredientUnitRepository ingredientUnitRepository;
//
//    @Autowired
//    private IngredientChangeRepository ingredientChangeRepository;
//    @Override
//    public Page<IngredientVo> findAllIngredient(String searchData, Integer pageIndex, Integer pageSize ) {
//        if (searchData == null) {
//            searchData = "";
//        }
//        Pageable pageable = PageRequest.of(pageIndex, pageSize);
//        return ingredientRepository.findAll("%"+searchData.trim()+"%", pageable);
//    }
//
//    @Override
//    public List<IngredientCategoryVo> getListIngredientCategory() {
//        return ingredientCategoryRepository.getListIngredientCategory();
//    }
//
//    @Override
//    public List<IngredientUnitVo> getListIngredientUnit() {
//        return ingredientUnitRepository.getListIngredientUnit();
//    }
//
//    @Override
//    public Ingredient save(SaveIngredientRequest saveIngredientRequest) {
//        Ingredient ingredient = new Ingredient();
//        if(saveIngredientRequest.getIngredientID() != null) {
//            ingredient.setIngredientID(saveIngredientRequest.getIngredientID());
//        }
//        ingredient.setIngredientPrice(saveIngredientRequest.getIngredientPrice());
//        ingredient.setCalo(saveIngredientRequest.getCalo());
//        ingredient.setName(saveIngredientRequest.getName());
//        ingredient.setDeleteFlag(0);
//        IngredientCategory ingredientCategory = ingredientCategoryRepository.findByIngredientCateID(saveIngredientRequest.getIngredientCategoryID());
//        IngredientUnit ingredientUnit = ingredientUnitRepository.findByIngredientUnitID(saveIngredientRequest.getIngredientUnitID());
//        ingredient.setIngredientCategoryID(ingredientCategory);
//        ingredient.setIngredientUnitID(ingredientUnit);
//        return ingredientRepository.save(ingredient);
//    }
//
//    @Override
//    public IngredientVo getIngredientDetail(Integer ingredientID) {
//        return ingredientRepository.findByIngredientID(ingredientID);
//    }
//
//    @Override
//    @Transactional
//    public Boolean deleteIngredient(Integer ingredientID) {
//            IngredientVo ingredient = ingredientRepository.findByIngredientID(ingredientID);
//            if(ingredient != null){
//                    ingredientRepository.updateDeleteFlag(ingredientID);
//                    return  true;
//            }
//            return false;
//    }
//
//
//
////    @Override
////    @Transactional
////    public Boolean deleteIngredient(Integer ingredientID) {
////            IngredientVo ingredient = ingredientRepository.findByIngredientID(ingredientID);
////            if(ingredient != null){
////                    ingredientChangeRepository.deleteByIngredientID(ingredientID);
////                    ingredientDetailRepository.deleteByIngredientID(ingredientID);
////                    ingredientRepository.deleteByIngredientID(ingredientID);
////                    return  true;
////            }
////            return false;
////    }
//
//
//}
