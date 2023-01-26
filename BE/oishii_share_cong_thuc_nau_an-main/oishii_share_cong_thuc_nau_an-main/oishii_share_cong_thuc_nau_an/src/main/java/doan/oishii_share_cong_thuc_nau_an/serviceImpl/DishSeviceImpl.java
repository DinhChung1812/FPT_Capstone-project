package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIDishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.*;
import doan.oishii_share_cong_thuc_nau_an.entities.*;
import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.repositories.*;
import doan.oishii_share_cong_thuc_nau_an.service.DishServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
//@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
public class DishSeviceImpl implements DishServive {

    @Autowired
    private DishTopVoRepository dishTopVoRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private DishImageRepository dishImageRepository;

    @Autowired
    private IngredientDetailRepository ingredientDetailRepository;

    @Autowired
    private IngredientChangeRepository ingredientChangeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    DishCommentServiceImpl commentService;

    @Autowired
    DishCommentRepository dishCommentRepository;

    @Override
    public List<DishVo> getTop5VoteWeek() {
        return dishTopVoRepository.getTop5Dish(7);
    }

    @Override
    public List<DishVo> getTop5VoteMonth() {
        return dishTopVoRepository.getTop5Dish(30);
    }

    @Override
    public Page<DishFormulaVo> getAllRecipe(String searchData, Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return dishRepository.findAllRecipe("%" + searchData.trim() + "%", pageable);
    }

    @Override
    public Page<DishFormulaVo> getRecipeByCategory(Integer categoryId, Integer pageIndex, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return dishRepository.getRecipeByCategory(categoryId, pageable);
    }

    @Override
    public Page<DishFormulaVo> getRecipeOfCreater(String creater, String searchData, Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return dishRepository.getRecipeOfCreater(creater, "%" + searchData.trim() + "%", pageable);
    }

    @Override
    public DishDetailVo getDishDetail(Integer dishID) {
        return dishRepository.getDishDetail(dishID);
    }

    public DishSearchResponse getDishByName(String name, Integer pageIndex) {
        if(name.trim()==null||name.trim().isEmpty()){
            throw new NotFoundException(StatusCode.Not_Found, "Bạn vui lòng nhập tên món ăn cần tìm kiếm");
        }
        if (pageIndex == null || pageIndex <= 0) {
            pageIndex = 1;
        }
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        List<Dish> dishList = dishRepository.findDishByNameOrMainIngredientLike(name.trim(), name.trim(), pageable);
        if (dishList.isEmpty()) {
            throw new NotFoundException(StatusCode.Not_Found, "Không tìm thấy món ăn phù hợp cho " + name);
        }
        List<DishResponse> dishResponseList = new ArrayList<>();
        for (Dish d : dishList) {
            DishResponse dishResponse = new DishResponse();
            dishResponse.setDishID(d.getDishID());
            dishResponse.setName(d.getName());
            dishResponse.setLevel(d.getLevel());
            dishResponse.setSize(d.getSize());
            dishResponse.setDescribe(d.getFormulaId().getDescribe());
            dishResponse.setTime(d.getTime());
            dishResponse.setVerifier(d.getFormulaId().getAccount().getName());
            dishResponse.setCreateDate(d.getCreateDate());

            double star = 0;
            int quantity = 0;
            double starRate = 0;
            if (!d.getListDishComment().isEmpty()) {
                for (DishComment dc : d.getListDishComment()) {
                    if (dc.getStartRate() != null) {
                        quantity++;
                        star += dc.getStartRate();
                    }
                }

                starRate = star / quantity;
            }

            BigDecimal bd = new BigDecimal(starRate).setScale(2, RoundingMode.HALF_UP);
            double formatStarRate = bd.doubleValue();

            dishResponse.setAvgStarRate(formatStarRate);

            dishResponse.setQuantityRate(quantity);


            for (DishImage di : d.getListDishImage()) {
                dishResponse.setImage(di.getUrl());
                break;
            }
            dishResponseList.add(dishResponse);
        }

        Double page = Double.valueOf(dishRepository.totalPage(name.trim(), name.trim()));
        int totalPage = 0;
        if(page%10==0){
            totalPage=(int)(page/10);
        }else {
            totalPage=(int)(page/10)+1;
        }

        DishSearchResponse dishSearchResponse = new DishSearchResponse();
        dishSearchResponse.setDishResponseList(dishResponseList);
        dishSearchResponse.setNumOfPages(totalPage);
        dishSearchResponse.setPageIndex(pageIndex);

        return dishSearchResponse;
    }

    public DishSearchResponse getDishByCate(Integer cate, Integer pageIndex) throws NotFoundException {
        if (pageIndex == null || pageIndex <= 0) {
            pageIndex = 1;
        }
        Pageable pageable = PageRequest.of(pageIndex - 1, 5);
        List<Dish> dishList = dishRepository.findDishByDishCategory(cate, pageable);
        if (dishList.isEmpty()) {
            throw new NotFoundException(StatusCode.Not_Found, "Không có món ăn cho thể loại này");
        }

        List<DishResponse> dishResponseList = new ArrayList<>();
        for (Dish d : dishList) {
            DishResponse dishResponse = new DishResponse();
            dishResponse.setDishID(d.getDishID());
            dishResponse.setName(d.getName());
            dishResponse.setLevel(d.getLevel());
            dishResponse.setSize(d.getSize());
            dishResponse.setDescribe(d.getFormulaId().getDescribe());
            dishResponse.setTime(d.getTime());
            dishResponse.setVerifier(d.getFormulaId().getAccount().getName());
            dishResponse.setCreateDate(d.getCreateDate());

            double star = 0;
            int quantity = 0;
            double starRate = 0;
            if (!d.getListDishComment().isEmpty()) {
                for (DishComment dc : d.getListDishComment()) {
                    if (dc.getStartRate() != null) {
                        quantity++;
                        star += dc.getStartRate();
                    }
                }

                starRate = star / quantity;
            }

            BigDecimal bd = new BigDecimal(starRate).setScale(2, RoundingMode.HALF_UP);
            double formatStarRate = bd.doubleValue();

            dishResponse.setAvgStarRate(formatStarRate);

            dishResponse.setQuantityRate(quantity);

            for (DishImage di : d.getListDishImage()) {
                dishResponse.setImage(di.getUrl());
            }
            dishResponseList.add(dishResponse);
        }

        Double page = Double.valueOf(dishRepository.totalPageSeacrchByCate(cate));
        int totalPage = 0;
        if(page%10==0){
            totalPage=(int)(page/10);
        }else {
            totalPage=(int)(page/10)+1;
        }

        DishSearchResponse dishSearchResponse = new DishSearchResponse();
        dishSearchResponse.setDishResponseList(dishResponseList);
        dishSearchResponse.setNumOfPages(totalPage);
        dishSearchResponse.setPageIndex(pageIndex);

        return dishSearchResponse;
    }

    public List<DishResponse> getTop5New() {
        List<Dish> dishList = dishRepository.getTop5ByNew();
        if (dishList.isEmpty()) {
            throw new NotFoundException(StatusCode.Not_Found, "không tìm thấy món mới nhất!!!");
        }
        List<DishResponse> dishResponseList = new ArrayList<>();
        for (Dish d : dishList) {
            DishResponse dishResponse = new DishResponse();
            dishResponse.setDishID(d.getDishID());
            dishResponse.setName(d.getName());
            dishResponse.setLevel(d.getLevel());
            dishResponse.setSize(d.getSize());
            dishResponse.setDescribe(d.getFormulaId().getDescribe());
            dishResponse.setTime(d.getTime());
            dishResponse.setVerifier(d.getFormulaId().getAccount().getUserName());
            dishResponse.setCreateDate(d.getCreateDate());

            double star = 0;
            int quantity = 0;
            double starRate = 0;
            if (!d.getListDishComment().isEmpty()) {
                for (DishComment dc : d.getListDishComment()) {
                    if (dc.getStartRate() != null) {
                        quantity++;
                        star += dc.getStartRate();
                    }
                }
                starRate = star / quantity;
            }

            BigDecimal bd = new BigDecimal(starRate).setScale(2, RoundingMode.HALF_UP);

            double formatStarRate = bd.doubleValue();

            dishResponse.setAvgStarRate(formatStarRate);

            dishResponse.setQuantityRate(quantity);

            for (DishImage di : d.getListDishImage()) {
                dishResponse.setImage(di.getUrl());
            }
            dishResponseList.add(dishResponse);
        }
        return dishResponseList;
    }


    public ResponseEntity<?> createNewRecipe(Dish dishRequest) {
        Dish dish = new Dish();
        if (dishRequest.getName().trim().isEmpty()) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu tên món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getLevel() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu độ khó món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getVideo().trim().isEmpty()) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu video món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getNumberPeopleForDish() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu số lượng người cho món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getTime() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thời gian món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getFormulaId() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu công thức món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getListIngredientDetail() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu nguyên liệu món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getListDishImage() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu ảnh mô tả món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getIdDishCategory() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thể loại món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRepository.existsDishByNameAndStatus(dishRequest.getName(), 1)) {
            return new ResponseEntity<>(new MessageResponse(StatusCode.Duplicate, "Tên món ăn bị trùng với món ăn khác"), HttpStatus.BAD_REQUEST);
        }

        dish.setName(dishRequest.getName());
        dish.setOrigin(dishRequest.getOrigin());

        int totalCalo = 0;
        for (IngredientDetail d : dishRequest.getListIngredientDetail()) {
            totalCalo += d.getCalo();
        }

        dish.setCalo(totalCalo);
        dish.setLevel(dishRequest.getLevel());
        dish.setNumberPeopleForDish(dishRequest.getNumberPeopleForDish());
        dish.setSize(dishRequest.getSize());
        dish.setTime(dishRequest.getTime());
        dish.setVideo(dishRequest.getVideo());
        dish.setCreateDate(LocalDate.now());
        dish.setStatus(1);

        Formula formula = insertFormulaAndStep(dishRequest);
        dish.setFormulaId(formula);
        int dishId = dishRepository.save(dish).getDishID();
        dish.setDishID(dishId);
        dishRequest.setDishID(dishId);

        insertIngredientDetailAndIngredientChange(dishRequest);

        insertCategory(dish.getDishID(), dishRequest);
        insertImage(dishRequest);
        return ResponseEntity.ok(new MessageResponse(StatusCode.Success, "Thêm món ăn thành công"));
    }

    @Override
    public ResponseEntity<?> deleteRecipe(Integer recipeId) {
        if (recipeId == null || recipeId == 0 || recipeId.equals("") || Character.isDigit(recipeId)) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Không tìm thấy món ăn"), HttpStatus.BAD_REQUEST);
        }

        dishRepository.deleteRecipe(recipeId);
        return ResponseEntity.ok(new MessageResponse(StatusCode.Success, "Xóa món ăn thành công"));
    }

    void insertIngredientDetailAndIngredientChange(Dish dishRequest) {
        List<IngredientDetail> ingredientDetailList = dishRequest.getListIngredientDetail();
        for (IngredientDetail detail : ingredientDetailList) {
            IngredientDetail ingredientDetail = new IngredientDetail();
            ingredientDetail.setName(detail.getName());
            ingredientDetail.setQuantity(detail.getQuantity());
            ingredientDetail.setUnit(detail.getUnit());
            ingredientDetail.setCalo(detail.getCalo());
            ingredientDetail.setMainIngredient(detail.getMainIngredient());
            ingredientDetail.setDishID(dishRequest);
            ingredientDetail.setIngredientDetailID(ingredientDetailRepository.save(ingredientDetail).getIngredientDetailID());

            if (detail.getIngredientChangeList() != null) {
                for (IngredientChange ic : detail.getIngredientChangeList()) {
                    IngredientChange change = new IngredientChange();
                    change.setName(ic.getName());
                    change.setQuantity(ic.getQuantity());
                    change.setUnit(ic.getUnit());
                    change.setCalo(ic.getCalo());
                    change.setIngredientDetail(ingredientDetail);
                    ingredientChangeRepository.save(change);
                }
            }

        }
    }

    void insertImage(Dish dishRequest) {
        List<DishImage> dishImages = dishRequest.getListDishImage();
        for (DishImage d : dishImages) {
            DishImage dishImage = new DishImage();
            dishImage.setUrl(d.getUrl());
            dishImage.setNote(d.getNote());
            dishImage.setDishID(dishRequest);
            dishImageRepository.save(dishImage);
        }
    }

    void insertCategory(int dishId, Dish dishRequest) {
        List<DishCategory> dishCategories = dishRequest.getIdDishCategory();
        for (DishCategory dc : dishCategories) {
            dishRepository.insertDishCategory(dishId, dc.getDishCategoryID());
        }
    }

    Formula insertFormulaAndStep(Dish dishRequest) {
        Formula formula = new Formula();

        formula.setDescribe(dishRequest.getFormulaId().getDescribe());
        formula.setSummary(dishRequest.getFormulaId().getSummary());

        Account account = accountRepository.findAccountByUserName(dishRequest.getFormulaId().getAccount().getUserName());
        if (account == null) {
            throw new NotFoundException(StatusCode.Not_Found, "tài khoản không có quyền hoặc đã bị khóa!!!");
        }

        formula.setAccount(account);
        formula.setFormulaID(formulaRepository.save(formula).getFormulaID());


        List<Step> stepSet = dishRequest.getFormulaId().getListStep();
        for (Step s : stepSet) {
            Step step = new Step();

            step.setDescribe(s.getDescribe());
            step.setTitle(s.getTitle());
            step.setFormulaID(formula);
            stepRepository.save(step);
        }
        return formula;
    }

    @Override
    public ResponseEntity<?> editRecipe(Integer dishId, Dish dishRequest) {

        if (!dishRepository.existsDishByDishIDAndStatus(dishId, 1)) {
            return new ResponseEntity(new MessageResponse(StatusCode.Not_Found, "Không tìm thấy món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getName().trim().isEmpty()) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu tên món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getLevel() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu độ khó món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getVideo().trim().isEmpty()) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu video món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getNumberPeopleForDish() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu số lượng người cho món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getTime() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thời gian món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getFormulaId() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu công thức món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getListIngredientDetail() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu nguyên liệu món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getListDishImage() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu ảnh mô tả món ăn"), HttpStatus.BAD_REQUEST);
        } else if (dishRequest.getIdDishCategory() == null) {
            return new ResponseEntity(new MessageResponse(StatusCode.Lack_Of_Information, "Thiếu thể loại món ăn"), HttpStatus.BAD_REQUEST);
        }

        //delete step
        stepRepository.deleteStepByDishId(dishId);
        //delete IngredientDetail And IngredientChange
        ingredientChangeRepository.deleteAllIngredientChangeByDishId(dishId);
        ingredientDetailRepository.deleteIngredientDetailByDishID(dishId);

        //delete image and video
        dishImageRepository.deleteDishImageByDishId(dishId);
        //delete category
        categoryRepository.deleteDishCategoryByDishId(dishId);

        dishRequest.setDishID(dishId);

        int totalCalo = 0;
        for (IngredientDetail d : dishRequest.getListIngredientDetail()) {
            totalCalo += d.getCalo();
        }

        dishRepository.updateRecipe(dishRequest.getName(), totalCalo, dishRequest.getLevel(), dishRequest.getNumberPeopleForDish(), dishRequest.getSize(), dishRequest.getTime(), dishRequest.getVideo(), dishId);

        updateFormulaAndStep(dishRequest);

        updateIngredientDetailAndIngredientChange(dishRequest);

        updateCategory(dishId, dishRequest);
        updateImage(dishRequest);
        return ResponseEntity.ok(new MessageResponse(StatusCode.Success, "Cập nhật món ăn thành công"));
    }

    void updateIngredientDetailAndIngredientChange(Dish dishRequest) {
        List<IngredientDetail> ingredientDetailList = dishRequest.getListIngredientDetail();

        for (IngredientDetail detail : ingredientDetailList) {
            IngredientDetail ingredientDetail = new IngredientDetail();
            ingredientDetail.setName(detail.getName());
            ingredientDetail.setQuantity(detail.getQuantity());
            ingredientDetail.setUnit(detail.getUnit());
            ingredientDetail.setCalo(detail.getCalo());
            ingredientDetail.setDishID(dishRequest);
            ingredientDetail.setMainIngredient(detail.getMainIngredient());
            ingredientDetail.setIngredientDetailID(ingredientDetailRepository.save(ingredientDetail).getIngredientDetailID());

            if (detail.getIngredientChangeList() != null) {
                for (IngredientChange ic : detail.getIngredientChangeList()) {
                    IngredientChange change = new IngredientChange();
                    change.setName(ic.getName());
                    change.setQuantity(ic.getQuantity());
                    change.setUnit(ic.getUnit());
                    change.setCalo(ic.getCalo());
                    change.setIngredientDetail(ingredientDetail);
                    ingredientChangeRepository.save(change);
                }
            }

        }
    }

    void updateImage(Dish dishRequest) {
        List<DishImage> listImagesRequest = dishRequest.getListDishImage();

        for (DishImage d : listImagesRequest) {
            DishImage dishImage = new DishImage();
            dishImage.setUrl(d.getUrl());
            dishImage.setNote(d.getNote());
            dishImage.setDishID(dishRequest);
            dishImageRepository.save(dishImage);
        }
    }

    void updateCategory(int dishId, Dish dishRequest) {
        List<DishCategory> dishCategories = dishRequest.getIdDishCategory();
        for (DishCategory dc : dishCategories) {
            dishRepository.insertDishCategory(dishId, dc.getDishCategoryID());
        }
    }

    void updateFormulaAndStep(Dish dishRequest) {
        Formula formula = formulaRepository.findByDish_DishID(dishRequest.getDishID());
        formula.setDescribe(dishRequest.getFormulaId().getDescribe());
        formula.setSummary(dishRequest.getFormulaId().getSummary());
        formulaRepository.save(formula);

        List<Step> listStep = dishRequest.getFormulaId().getListStep();
        for (Step s : listStep) {
            Step step = new Step();
            step.setDescribe(s.getDescribe());
            step.setTitle(s.getTitle());
            step.setFormulaID(formula);
            stepRepository.save(step);
        }

    }

    @Override
    public List<BMIDishDetailVo> getDishByBMIUser(String meal, String mainIngredient, Double calo) {
        Double CaloDetail = 0.0;
        if (meal.equals("Bữa sáng")) {
            CaloDetail = calo * 35 / 100;
        } else if (meal.equals("Bữa trưa")) {
            CaloDetail = calo * 40 / 100;
        } else if (meal.equals("Bữa tối")) {
            CaloDetail = calo * 25 / 100;
        }

        int caloNew = (int) Math.round(CaloDetail);
        List<BMIDishDetailVo> dish = new ArrayList<BMIDishDetailVo>();
        List<BMIDishDetailVo> dishNew = new ArrayList<BMIDishDetailVo>();

        List<IngredientDetail> ingredent = ingredientDetailRepository.searchMainIngredient(mainIngredient);
        if (ingredent.size() > 0) {
            dish = dishRepository.getDishByBMIUser(meal, mainIngredient, caloNew);
            if (dish.size() == 0) {
                return null;
            }
            Set elementsAlreadySeen = new LinkedHashSet<>();
            dish.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
            dish = calAvgStartRate(dish);
            dishNew.add(dish.get(0));
            if (dishNew.size() == 0) {
                return null;
            }

        } else {
            throw new NotFoundException(StatusCode.Not_Found, "Nguyên liệu chính " + mainIngredient + " không tồn tại.");
        }
        //  }

        List<BMIDishDetailVo> BMIDishDetailVo = new ArrayList<>();
        for (int i = 0; i < dishNew.size(); i++) {
            BMIDishDetailVo dishDetailVo1 = new BMIDishDetailVo();
            dishDetailVo1 = getDishBMIDetail(dishNew.get(i).getDishID());
            if (meal.equals("Bữa sáng")) {
                dishDetailVo1.setTotalCaloBreak(CaloDetail);
                dishDetailVo1.setTotalRemainingCalo(CaloDetail - dishNew.get(i).getTotalCalo());

            }
            if (meal.equals("Bữa trưa")) {
                dishDetailVo1.setTotalCaloLunch(CaloDetail);
                dishDetailVo1.setTotalRemainingCalo(CaloDetail - dishNew.get(i).getTotalCalo());

            }
            if (meal.equals("Bữa tối")) {
                dishDetailVo1.setTotalCaloDinner(CaloDetail);
                dishDetailVo1.setTotalRemainingCalo(CaloDetail - dishNew.get(i).getTotalCalo());

            }
            if (meal.equals("Tráng miệng")) {
                dishDetailVo1.setTotalRemainingCalo(CaloDetail - dishNew.get(i).getTotalCalo());

            }
            dishDetailVo1.setTotalStarRate(dishNew.get(i).getTotalStarRate());
            dishDetailVo1.setNumberStartRateInDish(dishNew.get(i).getNumberStartRateInDish());
            dishDetailVo1.setAvgStartRate(dishNew.get(i).getAvgStartRate());
            dishDetailVo1.setDishCate(dishNew.get(i).getDishCate());
            dishDetailVo1.setDishImageList(dishImageRepository.findByDishID(dishDetailVo1.getDishID()));
            BMIDishDetailVo.add(dishDetailVo1);
        }
        return BMIDishDetailVo;
    }

    public List<BMIDishDetailVo> calAvgStartRate(List<BMIDishDetailVo> list){
        Set elementsAlreadySeen = new LinkedHashSet<>();
        list.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
        for (BMIDishDetailVo b: list ) {
            Integer startRate = dishRepository.getStartRateByDishID(b.getDishID());
            if(startRate == null){
                startRate = 0;
            }
            b.setTotalStarRate(startRate);
            Integer numberStartRate = dishRepository.getNumberStartRateByDishID(b.getDishID());
            if(numberStartRate == null){
                numberStartRate = 0;
            }
            b.setNumberStartRateInDish(numberStartRate);
            if(numberStartRate != 0){
                Double ab = Double.valueOf(b.getTotalStarRate());
                Double ac = Double.valueOf(b.getNumberStartRateInDish());
                //b.setAvgStartRate(Double.valueOf(b.getTotalStarRate()/b.getNumberStartRateInDish()));
                b.setAvgStartRate((double) Math.round((ab/ac) * 10) / 10);
            } else {
                b.setAvgStartRate(0.0);
            }
        }
        Collections.sort(list, new Comparator<BMIDishDetailVo>() {
            @Override
            public int compare(BMIDishDetailVo o1, BMIDishDetailVo o2) {
                return (int) Math.round(o2.getAvgStartRate() - o1.getAvgStartRate());
            }
        });

        return list;
    }

    public void getIngredientConflict (List<BMIDishDetailVo> listIngredientByDishID, List<BMIDishDetailVo> listIngredientConflict, List<String> listIngredientConflictMeal ){
        for (BMIDishDetailVo b : listIngredientByDishID) {
            for (BMIDishDetailVo bd : listIngredientConflict) {
                if (b.getNameIngredient().equals(bd.getIngredientA())) {
                    listIngredientConflictMeal.add(bd.getIngredientB());
                } else if (b.getNameIngredient().equals(bd.getIngredientB())) {
                    listIngredientConflictMeal.add(bd.getIngredientA());
                }
            }
        }
    }

    @Override
    public List<BMIDishDetailVo> getListDishByBMIUser(Double totalCalo) {
        Double breakfastCalo = totalCalo * 35 / 100;
        Double lunchCalo = totalCalo * 40 / 100;
        Double dinnerCalo = totalCalo * 25 / 100;

        int breakfastCaloNew = (int) Math.round(breakfastCalo);
        int lunchCaloNew = (int) Math.round(lunchCalo);
        int dinnerCaloNew = (int) Math.round(dinnerCalo);

        int breakfast = 0;
        int lunch = 0;
        int dinner = 0;

        List<BMIDishDetailVo> dish = new ArrayList<BMIDishDetailVo>();
        List<BMIDishDetailVo> dishNew = new ArrayList<BMIDishDetailVo>();

        List<Integer> listIdDishBreak = new ArrayList<>();
        List<Integer> listIdDishLunch = new ArrayList<>();
        List<Integer> listIdDishDinner = new ArrayList<>();
        List<BMIDishDetailVo> listIngredientConflict = new ArrayList<>(); // Danh sach cac nguyen lieu bị conflict cả A and B
        List<BMIDishDetailVo> listIngredientByDishID = new ArrayList<>();
        List<String> listIngredientConflictBreak = new ArrayList<>(); //Danh sach cac nguyen lieu B conflict
        List<String> listIngredientConflictLunch = new ArrayList<>(); //Danh sach cac nguyen lieu B conflict
        List<String> listIngredientConflictDinner = new ArrayList<>(); //Danh sach cac nguyen lieu B conflict

        listIngredientConflict = dishRepository.getIngredientConflict();
        if (listIngredientConflict.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "Không có nguyên liệu conflict nào!!");
        }

        List<BMIDishDetailVo> listDishBreakfastByBMIUser = dishRepository.getDishMCByBMIUser("Bữa sáng", breakfastCaloNew);
        if (listDishBreakfastByBMIUser.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "Data không có món ăn sáng phù hợp với lượng calo trên");
        } else {
            Set elementsAlreadySeen = new LinkedHashSet<>();
            listDishBreakfastByBMIUser.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
            listDishBreakfastByBMIUser = calAvgStartRate(listDishBreakfastByBMIUser);
            //Collections.shuffle(listDishBreakfastByBMIUser);
            dishNew.add(listDishBreakfastByBMIUser.get(0));
            listIngredientByDishID = dishRepository.getIngredientByDishID(listDishBreakfastByBMIUser.get(0).getDishID()); //lay danh sach nguyen lieu theo mon an
            if (listIngredientByDishID.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn");
            }
            getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictBreak);

            listIdDishBreak.add(listDishBreakfastByBMIUser.get(0).getDishID()); //check duplicate
            breakfast = breakfastCaloNew - listDishBreakfastByBMIUser.get(0).getTotalCalo();
            while (breakfast > 200) {
                dish = dishRepository.getDishMCByBMIUser("Bữa Sáng", breakfast);
                dish.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
                dish = calAvgStartRate(dish);
                if (dish.size() == 0) {
                    break;
                } else {
                    aa:
                    for (int i = 0; i < listIdDishBreak.size(); i++) {
                        for (BMIDishDetailVo b : dish) {
                            if (b.getDishID().intValue() == listIdDishBreak.get(i).intValue()) {
                                dish.remove(b);
                                continue aa;
                            }
                            if (dish.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Không còn món ăn phù hợp!!");
                            }
                        }
                    }
                }
                if (dish.size() == 0) {
                    //throw new NotFoundException(StatusCode.Not_Found, "Món ăn đã hết pls !!! Cần thêm data!!!");
                    break;
                }
                // Xoa cac mon an co nguyen lieu trung voi nguyen lieu bi conflict
                Boolean check = true;
                if (dish.size() >= 0) {
                    for (BMIDishDetailVo b : dish) {
                        listIngredientByDishID = dishRepository.getIngredientByDishID(b.getDishID());
                        for (BMIDishDetailVo b1 : listIngredientByDishID) {
                            for (int i = 0; i < listIngredientConflictBreak.size(); i++) {
                                if (b1.getNameIngredient().toLowerCase().equals(listIngredientConflictBreak.get(i).toLowerCase())) {
                                    check = false;
                                }
                            }
                        }
                        if (check == true) {
                            //Collections.shuffle(dish);
                            dishNew.add(b);
                            listIdDishBreak.add(b.getDishID());
                            listIngredientByDishID = dishRepository.getIngredientByDishID(b.getDishID());
                            if (listIngredientByDishID.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn");
                            }
                            getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictBreak);
                            breakfast = breakfast - b.getTotalCalo();
                            break;
                        } else {
                            dish.remove(b);
                            if (dish.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Món ăn đã hết pls ");
                            }
                        }
                    }
                } else {
                    throw new NotFoundException(StatusCode.Not_Found, "Data heet mon an");
                }
            }
            dish = dishRepository.getDishMCByBMIUser("Món Tráng Miệng", 300);
            dish = calAvgStartRate(dish);
            if (dish.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "Data không có món tráng miệng bữa sáng");
            } else {
                //Collections.shuffle(dish);
                dishNew.add(dish.get(0));
            }
        }

        //suggest bữa trưa
        List<BMIDishDetailVo> listDishLunchByBMIUser = dishRepository.getDishMCByBMIUser("Bữa Trưa", lunchCaloNew);
        if (listDishLunchByBMIUser.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "Data không có món ăn theo bữa trưa!!! Yêu cầu thêm!!!");
        } else {
            listDishLunchByBMIUser = calAvgStartRate(listDishLunchByBMIUser);
            for (BMIDishDetailVo l :listDishLunchByBMIUser) {
                if(l.getDishName().trim().equals("Cơm trắng")){
                    dishNew.add(l);
                    listIngredientByDishID = dishRepository.getIngredientByDishID(l.getDishID()); //lay danh sach nguyen lieu theo mon an
                    if (listIngredientByDishID.size() == 0) {
                        throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn " + l.getDishName());
                    }
                    getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictLunch);

                    listIdDishLunch.add(l.getDishID());
                    lunch = lunchCaloNew - l.getTotalCalo();
                    break;
                }
            }

            //Lay mon canh
            dish = dishRepository.getDishMCByBMIUser("Món Canh", lunch);
            //dish.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
            if (dish.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "Data không có món ăn canh bữa trưa!!! Yêu cầu thêm!!!");
            } else {
                dish = calAvgStartRate(dish);
                dishNew.add(dish.get(0));
                listIngredientByDishID = dishRepository.getIngredientByDishID(dish.get(0).getDishID()); //lay danh sach nguyen lieu theo mon an
                if (listIngredientByDishID.size() == 0) {
                    throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn " + dish.get(0).getDishName());
                }
                getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictLunch);

                listIdDishLunch.add(dish.get(0).getDishID());
                lunch = lunch - dish.get(0).getTotalCalo();
            }

            while (lunch > 200) {
                dish = dishRepository.getDishMCByBMIUser("Bữa Trưa", lunch);
                //dish.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
                if (dish.size() == 0) {
                    break;
                } else {
                    dish = calAvgStartRate(dish);
                    aa:
                    for (int i = 0; i < listIdDishLunch.size(); i++) {
                        for (BMIDishDetailVo b : dish) {
                            if (b.getDishID().intValue() == listIdDishLunch.get(i).intValue()) {
                                dish.remove(b);
                                continue aa;
                            }
                            if (dish.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Không còn món ăn phù hợp bữa trưa!!");
                            }
                        }
                    }
                }
                if (dish.size() == 0) {
                    break;
                }
                // Xoa cac mon an co nguyen lieu trung voi nguyen lieu bi conflict
                Boolean check = true;
                if (dish.size() >= 0) {
                    for (BMIDishDetailVo b : dish) {
                        listIngredientByDishID = dishRepository.getIngredientByDishID(b.getDishID());
                        for (BMIDishDetailVo b1 : listIngredientByDishID) {
                            for (int i = 0; i < listIngredientConflictLunch.size(); i++) {
                                if (b1.getNameIngredient().toLowerCase().equals(listIngredientConflictLunch.get(i).toLowerCase())) {
                                    check = false;
                                }
                            }
                        }
                        if (check == true) {
                            //Collections.shuffle(dish);
                            dishNew.add(b);
                            listIdDishLunch.add(b.getDishID());
                            listIngredientByDishID = dishRepository.getIngredientByDishID(b.getDishID());
                            if (listIngredientByDishID.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn");
                            }
                            getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictLunch);
                            lunch = lunch - dish.get(0).getTotalCalo();
                            break;
                        } else {
                            dish.remove(b);
                            if (dish.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Món ăn đã hết pls ");
                            }
                        }
                    }
                } else {
                    throw new NotFoundException(StatusCode.Not_Found, "Data hết món ăn");
                }
            }
            dish = dishRepository.getDishMCByBMIUser("Món Tráng Miệng", 300);
            dish = calAvgStartRate(dish);
            if (dish.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "Data không có món tráng miệng bữa trưa");
            } else {
                //Collections.shuffle(dish);
                dishNew.add(dish.get(0));
            }
        }

        //suggest bữa tối
        List<BMIDishDetailVo> listDishDinnerByBMIUser = dishRepository.getDishMCByBMIUser("Bữa Tối", dinnerCaloNew);
        if (listDishDinnerByBMIUser.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "Data không có món ăn theo bữa tối nào!!! Yêu cầu thêm!!!");
        } else {
            listDishDinnerByBMIUser = calAvgStartRate(listDishDinnerByBMIUser);

            for (BMIDishDetailVo l :listDishDinnerByBMIUser) {
                if(l.getDishName().trim().equals("Cơm trắng")){
                    dishNew.add(l);
                    listIngredientByDishID = dishRepository.getIngredientByDishID(l.getDishID()); //lay danh sach nguyen lieu theo mon an
                    if (listIngredientByDishID.size() == 0) {
                        throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn " + l.getDishName());
                    }
                    getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictDinner);

                    listIdDishDinner.add(l.getDishID());
                    dinner = dinnerCaloNew - l.getTotalCalo();
                    break;
                }
            }


            //Lay mon canh
            dish = dishRepository.getDishMCByBMIUser("Món Canh", dinner);
            //dish.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
            if (dish.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "Data không có món ăn canh bữa tối!!! Yêu cầu thêm!!!");
            } else {
                dish = calAvgStartRate(dish);
                dishNew.add(dish.get(0));
                listIngredientByDishID = dishRepository.getIngredientByDishID(dish.get(0).getDishID()); //lay danh sach nguyen lieu theo mon an
                if (listIngredientByDishID.size() == 0) {
                    throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn " + dish.get(0).getDishName());
                }
                getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictDinner);
                listIdDishDinner.add(dish.get(0).getDishID());
                dinner = dinner - dish.get(0).getTotalCalo();
            }

            while (dinner > 200) {
                dish = dishRepository.getDishMCByBMIUser("Bữa Tối", dinner);
                //dish.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
                if (dish.size() == 0) {
                    break;
                } else {
                    dish = calAvgStartRate(dish);
                    aa:
                    for (int i = 0; i < listIdDishDinner.size(); i++) {
                        for (BMIDishDetailVo b : dish) {
                            if (b.getDishID().intValue() == listIdDishDinner.get(i).intValue()) {
                                dish.remove(b);
                                continue aa;
                            }
                            if (dish.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Không còn món ăn phù hợp bữa tối!!");
                            }
                        }
                    }
                }
                if (dish.size() == 0) {
                    //throw new NotFoundException(StatusCode.Not_Found, "Món ăn đã hết pls !!! Cần thêm data!!!");
                    break;
                }
                // Xoa cac mon an co nguyen lieu trung voi nguyen lieu bi conflict
                Boolean check = true;
                if (dish.size() >= 0) {
                    for (BMIDishDetailVo b : dish) {
                        listIngredientByDishID = dishRepository.getIngredientByDishID(b.getDishID());
                        for (BMIDishDetailVo b1 : listIngredientByDishID) {
                            for (int i = 0; i < listIngredientConflictDinner.size(); i++) {
                                if (b1.getNameIngredient().toLowerCase().equals(listIngredientConflictDinner.get(i).toLowerCase())) {
                                    check = false;
                                }
                            }
                        }
                        if (check == true) {
                            //Collections.shuffle(dish);
                            dishNew.add(b);
                            listIdDishDinner.add(b.getDishID());
                            listIngredientByDishID = dishRepository.getIngredientByDishID(b.getDishID());
                            if (listIngredientByDishID.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn");
                            }
                            getIngredientConflict(listIngredientByDishID, listIngredientConflict, listIngredientConflictDinner);
                            dinner = dinner - dish.get(0).getTotalCalo();
                            break;
                        } else {
                            dish.remove(b);
                            if (dish.size() == 0) {
                                throw new NotFoundException(StatusCode.Not_Found, "Món ăn đã hết pls ");
                            }
                        }
                    }
                } else {
                    throw new NotFoundException(StatusCode.Not_Found, "Data hết món ăn bữa tối");
                }
            }
            dish = dishRepository.getDishMCByBMIUser("Món Tráng Miệng", 300);
            dish = calAvgStartRate(dish);
            if (dish.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "Data không có món tráng miệng bữa tối");
            } else {
                //Collections.shuffle(dish);
                dishNew.add(dish.get(0));
            }
        }
        List<BMIDishDetailVo> BMIDishDetailVo = new ArrayList<>();
        for (int i = 0; i < dishNew.size(); i++) {
            BMIDishDetailVo dishDetailVo1 = new BMIDishDetailVo();
            dishDetailVo1 = getDishBMIDetail(dishNew.get(i).getDishID());
            dishDetailVo1.setTotalStarRate(dishNew.get(i).getTotalStarRate());
            dishDetailVo1.setNumberStartRateInDish(dishNew.get(i).getNumberStartRateInDish());
            dishDetailVo1.setAvgStartRate(dishNew.get(i).getAvgStartRate());
            dishDetailVo1.setTotalCaloBreak(breakfastCalo);
            dishDetailVo1.setTotalCaloLunch(lunchCalo);
            dishDetailVo1.setTotalCaloDinner(dinnerCalo);
            dishDetailVo1.setDishCate(dishNew.get(i).getDishCate());
            dishDetailVo1.setDishImageList(dishImageRepository.findByDishID(dishDetailVo1.getDishID()));
            BMIDishDetailVo.add(dishDetailVo1);
        }
        return BMIDishDetailVo;
    }


    @Override
    public List<String> getListMainIngredient() {
        List<IngredientDetail> listAllMainIngredient = ingredientDetailRepository.getListMainIngredient();
        if (listAllMainIngredient.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "không tìm thấy nguyên liệu chính nào!!!");
        }
        List<String> listMainIngredient = new ArrayList<>();

        int count = 0;
        for (IngredientDetail i : listAllMainIngredient) {
            if (count < 12) {
                listMainIngredient.add(i.getName().trim());
                count++;
            }
        }
        Set elementsAlreadySeen = new LinkedHashSet<>();
        listMainIngredient.removeIf(s -> !elementsAlreadySeen.add(s));
        //List<String> listMainIngredient = List.newArrayList(Sets.newHashSet(listWithDuplicates));
        return listMainIngredient;
    }

    @Override
    public DishEditResponse getDishById(Integer id) {
        Dish dish = dishRepository.findByDishID(id);
        if (dish == null) {
            throw new NotFoundException(StatusCode.Not_Found, "không tìm thấy món ăn hoặc món ăn đã bị xóa!!!");
        }
        DishEditResponse response = new DishEditResponse();
        response.setDishID(dish.getDishID());
        response.setName(dish.getName());
        response.setOrigin(dish.getOrigin());
        response.setCalo(dish.getCalo());
        response.setLevel(dish.getLevel());
        response.setNumberPeopleForDish(dish.getNumberPeopleForDish());
        response.setSize(dish.getSize());
        response.setTime(dish.getTime());
        response.setVideo(dish.getVideo());
        response.setCreateDate(dish.getCreateDate());
        response.setStatus(dish.getStatus());

        FormulaResponse formula = new FormulaResponse();
        formula.setFormulaID(dish.getFormulaId().getFormulaID());
        formula.setDescribe(dish.getFormulaId().getDescribe());
        formula.setSummary(dish.getFormulaId().getSummary());
        formula.setCreatedBy(dish.getFormulaId().getAccount().getUserName());
        response.setFormula(formula);

        List<StepResponse> listStep = new ArrayList<>();
        for (Step s : dish.getFormulaId().getListStep()) {
            StepResponse step = new StepResponse();
            step.setStepID(s.getStepID());
            step.setDescribe(s.getDescribe());
            step.setTitle(s.getTitle());
            listStep.add(step);
        }
        response.setListStep(listStep);

        List<DishImageResponse> listImage = new ArrayList<>();
        for (DishImage i : dish.getListDishImage()) {
            DishImageResponse image = new DishImageResponse();
            image.setDishImageID(i.getDishImageID());
            image.setUrl(i.getUrl());
            image.setNote(i.getNote());
            listImage.add(image);
        }
        response.setListDishImage(listImage);


        List<DishCategoryResponse> listCategory = new ArrayList<>();
        for (DishCategory c : dish.getIdDishCategory()) {
            DishCategoryResponse category = new DishCategoryResponse();
            category.setDishCategoryID(c.getDishCategoryID());
            category.setName(c.getName());
            category.setDishCategoryImage(c.getDishCategoryImage());
            listCategory.add(category);
        }
        response.setListDishCategory(listCategory);


        List<IngredientDetailResponse> listIngredientDetail = new ArrayList<>();
        for (IngredientDetail i : dish.getListIngredientDetail()) {
            IngredientDetailResponse detailResponse = new IngredientDetailResponse();
            detailResponse.setIngredientDetailID(i.getIngredientDetailID());
            detailResponse.setName(i.getName());
            detailResponse.setQuantity(i.getQuantity());
            detailResponse.setUnit(i.getUnit());
            detailResponse.setCalo(i.getCalo());
            detailResponse.setMainIngredient(i.getMainIngredient());

            List<IngredientChangeResponse> listIngredientChange = new ArrayList<>();
            if (!i.getIngredientChangeList().isEmpty()) {
                for (IngredientChange ic : i.getIngredientChangeList()) {
                    IngredientChangeResponse changeResponse = new IngredientChangeResponse();
                    changeResponse.setIngredientChangeID(ic.getIngredientChangeID());
                    changeResponse.setName(ic.getName());
                    changeResponse.setQuantity(ic.getQuantity());
                    changeResponse.setUnit(ic.getUnit());
                    changeResponse.setCalo(ic.getCalo());
                    listIngredientChange.add(changeResponse);
                }
            }

            detailResponse.setListIngredientChange(listIngredientChange);
            listIngredientDetail.add(detailResponse);
        }
        response.setListIngredientDetail(listIngredientDetail);
        return response;
    }

    @Override
    public List<String> searchMainIngredient(String ingredient) {
        List<IngredientDetail> listAllMainIngredient = ingredientDetailRepository.searchMainIngredient(ingredient);
        if (listAllMainIngredient.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "Không có nguyên liệu chính nào phù hợp với '" + ingredient + "' hoặc nguyên liệu chính đã bị xóa!!!");
        }
        List<String> mainIngredient = new ArrayList<>();
        for (IngredientDetail i : listAllMainIngredient) {
            mainIngredient.add(i.getName().trim());
        }
        Set elementsAlreadySeen = new LinkedHashSet<>();
        mainIngredient.removeIf(s -> !elementsAlreadySeen.add(s));
        return mainIngredient;
    }

    @Override
    public BMIDishDetailVo getDishBMIDetail(Integer dishId) {
        return dishRepository.getDishBMIDetail(dishId);
    }

    @Override
    public BMIDishDetailVo getDishByCaloBMI(String meal, String mainIngredient, Double calo, Integer[] listIdDish) {
        int caloNew = (int) Math.round(calo);

        List<BMIDishDetailVo> listIngredientConflict = new ArrayList<>(); // Danh sach cac nguyen lieu bị conflict cả A and B
        List<BMIDishDetailVo> listIngredientByDishID = new ArrayList<>();
        List<String> listIngredientConflictFavourite = new ArrayList<>(); //Danh sach cac nguyen lieu B conflict

        listIngredientConflict = dishRepository.getIngredientConflict();
        if (listIngredientConflict.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "Không có nguyên liệu conflict nào!!");
        }

        List<BMIDishDetailVo> listBMIDishDetail = new ArrayList<BMIDishDetailVo>();
        if (caloNew > 300) {
            List<IngredientDetail> ingredent = ingredientDetailRepository.searchMainIngredient(mainIngredient);
            if (ingredent.size() > 0) {
                listBMIDishDetail = dishRepository.getDishByBMIUser(meal, mainIngredient, caloNew);
                Set elementsAlreadySeen = new LinkedHashSet<>();
                listBMIDishDetail.removeIf(s -> !elementsAlreadySeen.add(s.getDishID()));
            } else {
                throw new NotFoundException(StatusCode.Not_Found, "Nguyên liệu chính " + mainIngredient + " không tồn tại.");
            }
        } else {
            listBMIDishDetail = dishRepository.getDishMCByBMIUser("Món Tráng Miệng", 300);
        }

        // check duplicate
        if (listBMIDishDetail.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "không tìm thấy món ăn phù hợp với lượng calo trên hoặc món ăn đã bị xóa!!!");
        } else {
            listBMIDishDetail = calAvgStartRate(listBMIDishDetail);
            aa:
            for (int i = 0; i < listIdDish.length; i++) {
                for (BMIDishDetailVo b : listBMIDishDetail) {
                    if (b.getDishID().intValue() == listIdDish[i].intValue()) {
                        listBMIDishDetail.remove(b);
                        continue aa;
                    }
                    if (listBMIDishDetail.size() == 0) {
                        throw new NotFoundException(StatusCode.Not_Found, "Không còn món ăn phù hợp!!");
                    }
                }
            }

            if (listBMIDishDetail.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "không tìm thấy món ăn phù hợp với lượng calo trên hoặc món ăn đã bị xóa hoặc bị trùng món ăn!!!");
            }

        }

        for (int i = 0; i < listIdDish.length; i++) {
            listIngredientByDishID = dishRepository.getIngredientByDishID(listIdDish[i]); //lay danh sach nguyen lieu theo mon an
            if (listIngredientByDishID.size() == 0) {
                throw new NotFoundException(StatusCode.Not_Found, "Không lấy ra được nguyên liệu theo món ăn");
            }
            for (BMIDishDetailVo b : listIngredientByDishID) {
                for (BMIDishDetailVo bd : listIngredientConflict) {
                    if (b.getNameIngredient().equals(bd.getIngredientA())) {
                        listIngredientConflictFavourite.add(bd.getIngredientB());
                    } else if (b.getNameIngredient().equals(bd.getIngredientB())) {
                        listIngredientConflictFavourite.add(bd.getIngredientA());
                    }
                }
            }
        }

        Boolean check = true;
        for (BMIDishDetailVo b : listBMIDishDetail) {
            listIngredientByDishID = dishRepository.getIngredientByDishID(b.getDishID());
            for (BMIDishDetailVo b1 : listIngredientByDishID) {
                for (int i = 0; i < listIngredientConflictFavourite.size(); i++) {
                    if (b1.getNameIngredient().equals(listIngredientConflictFavourite.get(i))) {
                        check = false;
                    }
                }
            }
            if (check == false) {
                listBMIDishDetail.remove(b);
                if (listBMIDishDetail.size() == 0) {
                    throw new NotFoundException(StatusCode.Not_Found, "Không có món ăn phù hợp hoặc nguyên liệu bị xung khắc ");
                }
            }
        }


        BMIDishDetailVo BMIDishDetail = new BMIDishDetailVo();
        //Collections.shuffle(listBMIDishDetail);
        BMIDishDetail = listBMIDishDetail.get(0);
        BMIDishDetail.setTotalStarRate(listBMIDishDetail.get(0).getTotalStarRate());
        BMIDishDetail.setNumberStartRateInDish(listBMIDishDetail.get(0).getNumberStartRateInDish());
        BMIDishDetail.setAvgStartRate(listBMIDishDetail.get(0).getAvgStartRate());
        BMIDishDetail.setTotalRemainingCalo(calo - listBMIDishDetail.get(0).getTotalCalo());
        BMIDishDetail.setDishImageList(dishImageRepository.findByDishID(BMIDishDetail.getDishID()));
        return BMIDishDetail;
    }

    @Override
    public List<String> getListMainIngredientByMeal(String meal) {
        List<IngredientDetail> listAllMainIngredient = ingredientDetailRepository.getListMainIngredientByMeal(meal);
        if (listAllMainIngredient.size() == 0) {
            throw new NotFoundException(StatusCode.Not_Found, "không tìm thấy nguyên liệu chính nào!!!");
        }
        List<String> listMainIngredient = new ArrayList<>();

        int count = 0;
        for (IngredientDetail i : listAllMainIngredient) {
            if (count < 12) {
                listMainIngredient.add(i.getName().trim());
                count++;
            }
        }
        Set elementsAlreadySeen = new LinkedHashSet<>();
        listMainIngredient.removeIf(s -> !elementsAlreadySeen.add(s));
        //List<String> listMainIngredient = List.newArrayList(Sets.newHashSet(listWithDuplicates));
        return listMainIngredient;
    }
}
