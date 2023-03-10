package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.*;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.entities.CheckLikeDislikeReportId;
import doan.oishii_share_cong_thuc_nau_an.entities.FavouriteRecipe;
import doan.oishii_share_cong_thuc_nau_an.entities.Formula;
import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.repositories.*;
import doan.oishii_share_cong_thuc_nau_an.service.DishImageService;
import doan.oishii_share_cong_thuc_nau_an.service.FavoriteRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteRecipeServiceImpl implements FavoriteRecipeService {

    @Autowired
    private DishTopVoRepository dishTopVoRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private FavouriteRecipeRepository favouriteRecipeRepository;

    @Autowired
    private DishCommentRepository dishCommentRepository;

    @Autowired
    private DishImageService dishImageService;

    @Override
    public Page<DishVo> getFavoriteRecipe(Integer accountId,String searchData, Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<DishVo> listFavorite =  dishTopVoRepository.getFavoriteRecipe(accountId, "%" + searchData.trim() + "%", pageable);
        for(DishVo dishVo : listFavorite){
            dishVo.setTotalStarRate(0);
            List<DishCommentAccountVo> dishCommentAccountVoList = dishCommentRepository.findDishCommentByDishId(dishVo.getDishId());
            for(DishCommentAccountVo dishCommentAccountVo : dishCommentAccountVoList){
                dishVo.setTotalStarRate(dishVo.getTotalStarRate() + dishCommentAccountVo.getStartRate());
            }
            dishVo.setNumberStartRateInDish(dishCommentAccountVoList.size());
            if(dishVo.getNumberStartRateInDish()>0) {
                Double avgStarRate = (double)dishVo.getTotalStarRate()/dishVo.getNumberStartRateInDish();
                dishVo.setAvgStarRate((double) Math.round(avgStarRate*100)/100);
            }else{
                dishVo.setAvgStarRate(0.0);
            }
            List<DishImageVo> imageList = dishImageService.findByDishID(dishVo.getDishId());
            if(null != imageList && imageList.size() != 0) {
                dishVo.setUrlImage(imageList.get(0).getUrl());
            }
        }
        return  listFavorite;

    }

    @Override
    public ResponseEntity<?> addFavoriteRecipes(Integer dishId, Account account) {
        DishDetailVo dishDetail = dishRepository.getDishDetail(dishId);
        if(dishDetail == null){
            return ResponseEntity.ok(new MessageVo("C??ng th???c n??y kh??ng t???n t???i", "error"));
        }



        Formula formula = formulaRepository.findById(dishDetail.getFormulaID()).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found,"formula " + dishDetail.getFormulaID() + " Not exist or formula was blocked "));

        List<Integer> checkFavouriteRecipe = favouriteRecipeRepository.checkFavouriteRecipe(dishDetail.getFormulaID(), account.getAccountId());
        if(checkFavouriteRecipe == null || checkFavouriteRecipe.size()==0){
            FavouriteRecipe favouriteRecipe = new FavouriteRecipe();
            favouriteRecipe.setFormula(formula);
            favouriteRecipe.setAccount(account);
            favouriteRecipe.setCheckId(new CheckLikeDislikeReportId(account.getAccountId(), dishDetail.getFormulaID()));
            favouriteRecipeRepository.save(favouriteRecipe);
            return ResponseEntity.ok(new MessageVo("???? th??m th??nh c??ng c??ng th???c v??o danh s??ch y??u th??ch", "success"));
        }else{
            return ResponseEntity.ok(new MessageVo("C??ng th???c n??y ???? ???????c th??m v??o danh s??ch y??u th??ch r???i", "error"));
        }

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeFavoriteRecipes(Integer dishId, Account account) {
        DishDetailVo dishDetail = dishRepository.getDishDetail(dishId);
        if(dishDetail == null){
            return ResponseEntity.ok(new MessageVo("C??ng th???c n??y kh??ng t???n t???i", "error"));
        }
        favouriteRecipeRepository.removeFavouriteRecipe(dishDetail.getFormulaID(), account.getAccountId());
        return ResponseEntity.ok(new MessageVo("???? b??? c??ng th???c ra kh???i danh s??ch y??u th??ch", "success"));
    }
}
