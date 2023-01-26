package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.exception.StatusCode;
import doan.oishii_share_cong_thuc_nau_an.exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.BMI.BMIRequest;
import doan.oishii_share_cong_thuc_nau_an.repositories.BMIRepository;
import doan.oishii_share_cong_thuc_nau_an.service.BMIService;
import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class BMIServiceImpl implements BMIService {

    @Autowired
    private BMIRepository bmiRepository;

    @Override
    public ResponseEntity<?> getInformationBMIByUser(String username) {
        Account account = bmiRepository.getInformationBMIUser(username);
        if( account.getGender() == null || account.getDob() == null){
            return ResponseEntity.ok(new MessageVo("Thông tin ngày tháng năm sinh hoặc giới tính của bạn đang trống. Yêu cầu update thông tin account.", "error"));
        }

        BMIVo bmiVo = new BMIVo();
        bmiVo.setUsername(account.getUserName());
        bmiVo.setName(account.getName());
        bmiVo.setGender(account.getGender());
        bmiVo.setHigh(account.getHigh());
        bmiVo.setWeight(account.getWeight());
        Double bmi = (account.getWeight())/((account.getHigh()/100)*2);
        bmiVo.setBMIIndex((double) Math.round(bmi * 100) / 100);

        if (bmiVo.getBMIIndex() < 18.5){
            bmiVo.setBMIStatus("Cân nặng thấp (gầy)");
        } else if(bmiVo.getBMIIndex() >= 18.5 && bmiVo.getBMIIndex() < 22.9){
            bmiVo.setBMIStatus("Cân nặng lí tưởng (bình thường)");
        } else if(bmiVo.getBMIIndex() >= 23 && bmiVo.getBMIIndex() < 24.9 ){
            bmiVo.setBMIStatus("Nguy cơ béo phì (tiền béo phì)");
        } else if(bmiVo.getBMIIndex() >= 25 && bmiVo.getBMIIndex() < 29.9 ){
            bmiVo.setBMIStatus("Béo phì độ I");
        } else if( bmiVo.getBMIIndex() >= 30 && bmiVo.getBMIIndex() < 40){
            bmiVo.setBMIStatus("Béo phì độ II");
        } else {
            bmiVo.setBMIStatus("Béo phì độ III");
        }
        bmiVo.setBMINote("Chỉ số BMI là chỉ số đo cân nặng của một người. Công thức BMI được áp dụng cho cả nam và nữ và chỉ áp dụng cho người trưởng thành (trên 18 tuổi), không áp dụng cho phụ nữ mang thai, vận động viên, người già và có sự thay đổi giữa các quốc gia.");
        bmiVo.setMobility(account.getMobility());
        bmiVo.setTotalCalo(account.getTotalCalo());
        bmiVo.setTarget(account.getTarget());
        bmiVo.setTagetIndex(account.getTargetIndex());
        bmiVo.setDob(account.getDob());
        return ResponseEntity.ok(bmiVo);
    }

    @Override
    public void updateProfile(BMIRequest bmiRequest) {
        if(bmiRequest.getHigh().equals("") || bmiRequest.getWeight().equals("") || bmiRequest.getDob().equals("") || bmiRequest.getGender().equals("") || bmiRequest.getR().equals("") || bmiRequest.getTarget().equals("")){
            throw new NotFoundException(StatusCode.Not_Found, "Bạn chưa nhập đủ thông tin BMI yêu cầu kiểm tra lại");
        }
        Account account = bmiRepository.findByUserName(bmiRequest.getUsername()).orElseThrow(() -> new NotFoundException(0,"Username " + bmiRequest.getUsername() + " Not exist or account was blocked "));
        Double totalCalo = 0.0;
        Double weight = bmiRequest.getWeight();
        Double high = bmiRequest.getHigh();
        Double r = bmiRequest.getR();
        int dob =(Period.between(bmiRequest.getDob(), LocalDate.now()).getYears());
        if(bmiRequest.getGender().equals("Nam")){
            totalCalo = (66 + (13.7 * weight) + (5 * high) -(6.8 * dob)) * r;
        } else if(bmiRequest.getGender().equals("Nữ")) {
            totalCalo = (655 + (9.6 * weight) + (1.8 * high) -(4.7 * dob)) * r;
        }

        if(bmiRequest.getTarget().equals("Tăng cân")){
            if(bmiRequest.getTargetIndex() == 0.5){
                totalCalo = totalCalo + 500;
            } else if(bmiRequest.getTargetIndex() == 1) {
                totalCalo = totalCalo + 1000;
            } else {
                totalCalo = totalCalo + 1500;
            }
        } else if (bmiRequest.getTarget().equals("Giảm cân")) {
            if(bmiRequest.getTargetIndex() == 0.5){
                totalCalo = totalCalo - 500;
            } else if(bmiRequest.getTargetIndex() == 1) {
                totalCalo = totalCalo - 1000;
            } else{
                totalCalo = totalCalo - 1500;
            }
        }

        account.setHigh(high);
        account.setWeight(weight);
        account.setMobility(r);
        account.setTotalCalo((double) Math.round(totalCalo));
        account.setTarget(bmiRequest.getTarget());
        account.setTargetIndex(bmiRequest.getTargetIndex());
        bmiRepository.save(account);
    }
}
