package doan.oishii_share_cong_thuc_nau_an.controller;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.DishCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TestController {

@Autowired
private DishCommentRepository dishCommentRepository;

//    @GetMapping ("/getcomment")
//    public ResponseEntity<?> index(Integer dishId){
//
//        //List<DishCommentAccountVo> dishCommentAccountVoList =dishCommentRepository.findDishCommentByDishId( dishId);
//        //return ResponseEntity.ok().body(dishCommentAccountVoList);
//    }
}
