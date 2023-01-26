//package doan.oishii_share_cong_thuc_nau_an.mapper;
//
//import doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish.DishImageResquest;
//import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishResponse;
//import doan.oishii_share_cong_thuc_nau_an.repositories.DishRepository;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.Dish;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.DishComment;
//import doan.oishii_share_cong_thuc_nau_an.web.entities.DishImage;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//@Data
//public class DishMapper {
//    @Autowired
//    private DishRepository dishRepository;
//    public void mapperDishToDishRequest(List<Dish> dishesList, List<DishResponse> DishResponseList) {
//        List<DishImageResquest> dishImageResquestList = null;
//        for (Dish d : dishesList) {
//            DishResponse dishResponse = new DishResponse();
//            dishResponse.setDishID(d.getDishID());
//            dishResponse.setName(d.getName());
////            dishResponse.setCalo(d.getCalo());
////            dishResponse.setLevel(d.getLevel());
////            dishResponse.setNumberPeopleForDish(d.getNumberPeopleForDish());
////            dishResponse.setOrigin(d.getOrigin());
//            dishResponse.setSize(d.getSize());
//            dishResponse.setCreateDate(d.getCreateDate());
//            dishImageResquestList = new ArrayList<>();
//            for (DishImage di : d.getListDishImage()) {
//                DishImageResquest dishImageResquest = new DishImageResquest();
//                dishImageResquest.setImageId(di.getDishImageID());
//                dishImageResquest.setNote(di.getNote());
//                dishImageResquest.setUrl(di.getUrl());
//                dishImageResquest.setDishId(d.getDishID());
//                dishImageResquestList.add(dishImageResquest);
//            }
////            dishResponse.setListDishImage(dishImageResquestList);
//            Set<DishComment> dishCommentList = d.getListDishComment();
////            List<Object> quantityRate = dishRepository.getRateById(d.getDishID());
//
////            dishResponse.setQuantityRate(quantityRate);
////            dishResponse.setStarRate(star/quantityRate);
//            DishResponseList.add(dishResponse);
//        }
//    }
//}
