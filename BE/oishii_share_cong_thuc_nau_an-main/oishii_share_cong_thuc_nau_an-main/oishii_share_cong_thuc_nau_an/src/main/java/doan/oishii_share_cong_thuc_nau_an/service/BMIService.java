package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.dto.Requests.BMI.BMIRequest;
import org.springframework.http.ResponseEntity;

public interface BMIService {

    ResponseEntity<?> getInformationBMIByUser(String username);

    public void updateProfile(BMIRequest bmiRequest);
}
