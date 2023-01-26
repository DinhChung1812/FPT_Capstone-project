package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientConflictVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.IngredientConflictRepository;
import doan.oishii_share_cong_thuc_nau_an.service.IngredientConflictService;
import doan.oishii_share_cong_thuc_nau_an.entities.IngredientConflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IngredientConflictServiceImlp implements IngredientConflictService {

    @Autowired
    private IngredientConflictRepository ingredientConflictRepository;

    @Override
    public Page<IngredientConflictVo> getListIngredientConflict(String searchData, Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return ingredientConflictRepository.getListIngredientConflict("%" + searchData.trim() + "%", pageable);
    }

    @Override
    public IngredientConflictVo getIngredientConflictInfo(Integer ingredientConflictId) {
        return ingredientConflictRepository.getIngredientConflictInfo(ingredientConflictId);
    }

    @Override
    public ResponseEntity<?> createIngredientConflict(String ingredientA, String ingredientB,String consequence, String userName) {

        List<IngredientConflictVo> ingredientConflictVoList =
                ingredientConflictRepository.checkIngredientConflictExist(ingredientA.toLowerCase().trim(), ingredientB.toLowerCase().trim());
        if(ingredientConflictVoList != null && ingredientConflictVoList.size() > 0){
            return ResponseEntity.ok(new MessageVo("Đã tồn tại cặp nguyên liệu xung khắc này trong danh sách", "error"));
        }
        IngredientConflict ingredientConflict = new IngredientConflict();
        ingredientConflict.setIngredientA(ingredientA.toLowerCase().trim());
        ingredientConflict.setIngredientB(ingredientB.toLowerCase().trim());
        ingredientConflict.setConsequence(consequence);
        ingredientConflict.setCreateUsername(userName);
        ingredientConflict.setUpdateUsername(userName);
        ingredientConflict.setCreateDate(LocalDate.now());
        ingredientConflict.setUpdateDate(LocalDate.now());
        ingredientConflictRepository.save(ingredientConflict);
        return ResponseEntity.ok(new MessageVo("Tạo cặp nguyên liệu xung khắc thành công", "success"));
    }

    @Override
    public ResponseEntity<?> updateIngredientConflict(Integer ingredientConflictId,String ingredientA,String ingredientB,String consequence, String userName) {
        IngredientConflict ingredientConflict = ingredientConflictRepository.findById(ingredientConflictId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found, "cặp nguyên liệu xung khắc này không tồn tại"));

            List<IngredientConflictVo> ingredientConflictVoList =
                    ingredientConflictRepository.checkIngredientConflictExist(ingredientA.toLowerCase().trim(), ingredientB.toLowerCase().trim());
            if (ingredientConflictVoList != null && ingredientConflictVoList.size() > 0) {
                for(IngredientConflictVo ingredientConflictVo : ingredientConflictVoList){
                    if(ingredientConflictVo.getIngredientConflictId() != ingredientConflict.getIngredientConflictId()){
                        return ResponseEntity.ok(new MessageVo("Đã tồn tại cặp nguyên liệu xung khắc này trong danh sách", "error"));
                    }
                }

            }

        ingredientConflict.setIngredientA(ingredientA.toLowerCase().trim());
        ingredientConflict.setIngredientB(ingredientB.toLowerCase().trim());
        ingredientConflict.setConsequence(consequence);
        ingredientConflict.setUpdateUsername(userName);
        ingredientConflict.setUpdateDate(LocalDate.now());
        ingredientConflictRepository.save(ingredientConflict);
        return ResponseEntity.ok(new MessageVo("Cập nhật cặp nguyên liệu xung khắc thành công", "success"));
    }

    @Override
    public ResponseEntity<?> deleteIngredientConflict(Integer ingredientConflictId) {
        IngredientConflict ingredientConflict = ingredientConflictRepository.findById(ingredientConflictId).orElseThrow(() ->
                new NotFoundException(StatusCode.Not_Found, "cặp nguyên liệu xung khắc này không tồn tại"));
        ingredientConflictRepository.deleteById(ingredientConflictId);
        return ResponseEntity.ok(new MessageVo("Xóa cặp nguyên liệu xung khắc thành công", "success"));
    }
}
